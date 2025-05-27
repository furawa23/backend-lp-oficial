package com.alexander.sistema_cerro_verde_backend.service.ventas.jpa;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alexander.sistema_cerro_verde_backend.entity.compras.Compras;
import com.alexander.sistema_cerro_verde_backend.entity.compras.MovimientosInventario;
import com.alexander.sistema_cerro_verde_backend.entity.compras.Productos;
import com.alexander.sistema_cerro_verde_backend.entity.ventas.Ventas;
import com.alexander.sistema_cerro_verde_backend.repository.compras.MovimientosInventarioRepository;
import com.alexander.sistema_cerro_verde_backend.repository.compras.ProductosRepository;
import com.alexander.sistema_cerro_verde_backend.repository.recepcion.HabitacionesRepository;
import com.alexander.sistema_cerro_verde_backend.repository.ventas.ClientesRepository;
import com.alexander.sistema_cerro_verde_backend.repository.ventas.VentasRepository;
import com.alexander.sistema_cerro_verde_backend.service.ventas.IVentaService;

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
    private MovimientosInventarioRepository repoMovimientosInventario;

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
    }

    @Override
    public void modificar(Ventas venta) {
        repoVenta.save(venta);
    }

    @Override
    public void eliminar(Integer id) {
        repoVenta.deleteById(id);
    }
}
