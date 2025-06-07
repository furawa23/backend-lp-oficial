package com.alexander.sistema_cerro_verde_backend.service.ventas.jpa;

import java.io.ByteArrayOutputStream;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alexander.sistema_cerro_verde_backend.entity.compras.MovimientosInventario;
import com.alexander.sistema_cerro_verde_backend.entity.ventas.DetalleVenta;
import com.alexander.sistema_cerro_verde_backend.entity.ventas.VentaHabitacion;
import com.alexander.sistema_cerro_verde_backend.entity.ventas.VentaSalon;
import com.alexander.sistema_cerro_verde_backend.entity.ventas.Ventas;
import com.alexander.sistema_cerro_verde_backend.repository.caja.CajasRepository;
import com.alexander.sistema_cerro_verde_backend.repository.compras.MovimientosInventarioRepository;
import com.alexander.sistema_cerro_verde_backend.repository.compras.ProductosRepository;
import com.alexander.sistema_cerro_verde_backend.repository.recepcion.HabitacionesRepository;
import com.alexander.sistema_cerro_verde_backend.repository.recepcion.ReservasRepository;
import com.alexander.sistema_cerro_verde_backend.repository.ventas.ClientesRepository;
import com.alexander.sistema_cerro_verde_backend.repository.ventas.DetalleVentaRepository;
import com.alexander.sistema_cerro_verde_backend.repository.ventas.MetodoPagoRepository;
import com.alexander.sistema_cerro_verde_backend.repository.ventas.VentaHabitacionRepository;
import com.alexander.sistema_cerro_verde_backend.repository.ventas.VentaSalonRepository;
import com.alexander.sistema_cerro_verde_backend.repository.ventas.VentasRepository;
import com.alexander.sistema_cerro_verde_backend.service.ventas.IVentaService;
import com.openhtmltopdf.pdfboxout.PdfRendererBuilder;

import jakarta.persistence.EntityNotFoundException;

@Service
public class VentaService implements IVentaService {

    @Autowired
    private VentasRepository repoVenta;

    @Autowired
    private HabitacionesRepository repoHabitacion;

    @Autowired
    private ClientesRepository clienteRepository;

    @Autowired
    private ProductosRepository repoProductos;

    @Autowired
    private ReservasRepository repoReservas;

    @Autowired
    private MovimientosInventarioRepository repoMovimientosInventario;

    @Autowired
    private MetodoPagoRepository repoMetodo;

    @Autowired
    private CajasRepository repoCaja;

    @Autowired
    private DetalleVentaRepository repoDetalle;

    @Autowired
    private VentaHabitacionRepository repoVentaHabitacion;

    @Autowired
    private VentaSalonRepository repoVentaSalon;

    @Override
    public List<Ventas> buscarTodos() {
        return repoVenta.findAll();
    }

    @Override
    public Optional<Ventas> buscarPorId(Integer id) {
        return repoVenta.findById(id);
    }

    @Override
    public void guardar(Ventas venta) {
        Ventas ventaGuardada = repoVenta.save(venta);
        // 2) Recorres cada detalle y ajustas el stock
        ventaGuardada.getDetalleVenta().forEach(det -> {
            Integer prodId = det.getProducto().getId_producto();
            var producto = repoProductos.findById(prodId)
                    .orElseThrow(() -> new EntityNotFoundException("Producto no existe: " + prodId));
            MovimientosInventario movimiento = new MovimientosInventario();
            movimiento.setProducto(producto);
            movimiento.setTipo_movimiento("Salida");
            movimiento.setFecha(venta.getFecha());
            movimiento.setVenta(ventaGuardada);

            repoMovimientosInventario.save(movimiento);

            // si tu unidad tiene equivalencia:
            int incremento = (int) (det.getCantidad());
            movimiento.setCantidad(incremento);

            producto.setStock(producto.getStock() - incremento);
            repoProductos.save(producto);
        });

        ventaGuardada.getVentaXReserva().forEach(r -> {
            Integer idReserva = r.getReserva().getId_reserva();
            var reserva = repoReservas.findById(idReserva).orElseThrow(() -> new EntityNotFoundException("RESERVAAAAAAASDASDASDASDASDAS: " + idReserva));
            reserva.setEstado_reserva("Completada");
            repoReservas.save(reserva);
        });

        var caja = repoCaja.findByEstadoCaja("abierta").orElseThrow(() -> new RuntimeException("Caja no encontrada"));;

        ventaGuardada.getVentaMetodoPago().forEach(m -> {
            Integer idMetodoPago = m.getMetodoPago().getIdMetodoPago();
            var metodoPago = repoMetodo.findById(idMetodoPago).orElseThrow(() -> new EntityNotFoundException("METODO DE PAGO: " + idMetodoPago));
            if (!metodoPago.getNombre().equals("Efectivo")) {
                caja.setSaldoTotal(caja.getSaldoTotal() + m.getPago());
                repoCaja.save(caja);
            }
        });
    }

    @Override
    public void modificar(Ventas venta) {
        repoVenta.save(venta);
    }

    @Override
    public void eliminar(Integer id) {
        repoVenta.deleteById(id);
    }

    @Override
    public String generarComprobante(Integer id) {
        String correlativo;
        String tipo;

        Ventas venta = repoVenta.findById(id).orElseThrow();

        if (venta.getComprobantePago().getNumComprobante().equals("B001")) {
            correlativo = venta.getComprobantePago().getNumSerieBoleta();
            tipo = "Boleta";
        } else {
            correlativo = venta.getComprobantePago().getNumSerieFactura();
            tipo = "Factura";
        }

        StringBuilder filas = new StringBuilder();

        // Obtener detalles
        List<DetalleVenta> productos = repoDetalle.findByVenta_IdVenta(id);
        List<VentaHabitacion> habitaciones = repoVentaHabitacion.findByVenta_IdVenta(id);
        List<VentaSalon> salones = repoVentaSalon.findByVenta_IdVenta(id);

        //Subtotal
        Double subTotal = 0.0;
        Double igv = 0.0;

        // Agregamos productos
        for (DetalleVenta d : productos) {
            subTotal = subTotal + d.getSubTotal();
            filas.append("<tr>")
                    .append("<td>").append(d.getCantidad()).append("</td>")
                    .append("<td>").append(d.getProducto().getNombre()).append("</td>")
                    .append("<td>S/ ").append(d.getProducto().getPrecioVenta()).append("</td>")
                    .append("<td>S/ ").append(d.getSubTotal()).append("</td>")
                    .append("</tr>");
        }

        // Agregamos habitaciones
        for (VentaHabitacion h : habitaciones) {
            subTotal = subTotal + h.getSubTotal();
            filas.append("<tr>")
                    .append("<td>1</td>")
                    .append("<td>").append(h.getHabitacion().getNumero())
                    .append(" Piso ").append(h.getHabitacion().getPiso())
                    .append(" ").append(h.getHabitacion().getTipo_habitacion().getNombre())
                    .append(" X ").append(h.getDias()).append(" dias").append("</td>")
                    .append("<td>S/ ").append(h.getHabitacion().getTipo_habitacion().getPrecio_publico()).append("</td>")
                    .append("<td>S/ ").append(h.getSubTotal()).append("</td>")
                    .append("</tr>");
        }

        // Agregamos salones
        for (VentaSalon s : salones) {
            subTotal = subTotal + s.getSubTotal();
            filas.append("<tr>")
                    .append("<td>1</td>")
                    .append("<td>").append(s.getSalon().getNombre())
                    .append(" X ").append(s.getDias()).append(" dias").append("</td>")
                    .append("<td>S/ ").append(s.getSalon().getPrecio_diario()).append("</td>")
                    .append("<td>S/ ").append(s.getSubTotal()).append("</td>")
                    .append("</tr>");
        }

        igv = subTotal * (venta.getIgv() / 100);
        String html = """
                <!DOCTYPE html>
<html lang="es">

<head>
    <meta charset="UTF-8"/>
    <style>
        @page {
            size: 50mm auto;
            margin: 5mm;
        }

        body {
            font-family: Arial, sans-serif;
            font-size: 10px;
        }

        .centrado {
            text-align: center;
        }

        .linea {
            border-top: 1px dashed #000;
            margin: 4px 0;
        }

        table {
            width: 100%;
            border-collapse: collapse;
        }

        td {
            padding: 2px 0;
        }

        .total {
            font-weight: bold;
            font-size: 11px;
        }
    </style>
</head>

<body>
    <div class="centrado">
        <img src="file:src/main/resources/static/img/logo-cerroverde2.png" width="100" alt="Logo"/><br/>
        <strong>CERRO VERDE TARAPOTO HOTEL S.A.C.</strong><br/>
        RUC: 20531481233<br/>
        Jr. Augusto B. Leguia Nro. 596<br/>
        ---------------------------------<br/>
        <strong>"""
                + tipo
                + """
        </strong><br/>
        """
                + venta.getComprobantePago().getNumComprobante()
                + """ 
         - 
         """
                + correlativo
                + """
         <br/>
        Fecha: """
                + venta.getFecha()
                + """
    </div>

    <div>
        <p style="margin: 0;">DNI/RUC: """ + venta.getCliente().getDniRuc()
                + """
         </p>
        <p style="margin: 0;">Cliente: """ + venta.getCliente().getNombre()
                + """ 
        </p>
    </div>

    <div class="linea"></div>

    <table>
        <thead>
            <th>Cant.</th>
            <th>Descrip.</th>
            <th>Precio U.</th>
            <th>Valor.</th>
        </thead>
        <tbody>
            """
                + filas.toString()
                + """
        </tbody>

    </table>

    <div class="linea"></div>

    <table>
        <tr>
            <td>Subtotal</td>
            <td style="text-align: right;">S/. """
                + subTotal.toString()
                + """
            </td>
        </tr>
        <tr>
            <td>Descuento</td>
            <td style="text-align: right;">S/. """
                + venta.getDescuento()
                + """
            </td>
        </tr>
        <tr>
            <td>IGV (18%)</td>
            <td style="text-align: right;">S/. """
                + igv.toString()
                + """
             </td>
        </tr>
        <tr>
            <td>Cargo</td>
            <td style="text-align: right;">S/. """
                + venta.getCargo()
                + """
            </td>
        </tr>
        <tr class="total">
            <td>Total</td>
            <td style="text-align: right;">S/. """
                + venta.getTotal()
                + """
            </td>
        </tr>
    </table>

    <div class="centrado">
        <div class="linea"></div>
        ¡Gracias por su compra!
    </div>
</body>

</html>
                """;
        return html;
    }

    @Override
    public byte[] generarPdf(Integer id) {
        String html = generarComprobante(id); // Este método debe generar el HTML como string

        try (ByteArrayOutputStream os = new ByteArrayOutputStream()) {
            PdfRendererBuilder builder = new PdfRendererBuilder();
            builder.withHtmlContent(html, null);
            builder.toStream(os);
            builder.run();
            return os.toByteArray();
        } catch (Exception e) {
            throw new RuntimeException("Error al generar PDF", e);
        }
    }
}
