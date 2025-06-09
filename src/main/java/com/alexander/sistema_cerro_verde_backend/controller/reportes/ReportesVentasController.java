package com.alexander.sistema_cerro_verde_backend.controller.reportes;

import java.util.List;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.alexander.sistema_cerro_verde_backend.entity.reportes.ClienteFrecuenteDTO;
import com.alexander.sistema_cerro_verde_backend.entity.reportes.HabitacionVentasDTO;
import com.alexander.sistema_cerro_verde_backend.entity.reportes.PagoVentasDTO;
import com.alexander.sistema_cerro_verde_backend.entity.reportes.ProductoVentasDTO;
import com.alexander.sistema_cerro_verde_backend.entity.reportes.SalonVentasDTO;
import com.alexander.sistema_cerro_verde_backend.service.reportes.jpa.ReportesVentasService;

@RestController
@RequestMapping("/hoteleria/reportes/ventas")
@CrossOrigin(origins = "http://localhost:4200")
public class ReportesVentasController {

    private final ReportesVentasService reportesVentasService;

    public ReportesVentasController(ReportesVentasService reportesVentasService) {
        this.reportesVentasService = reportesVentasService;
    }

    @GetMapping("/productos")
    public ResponseEntity<List<ProductoVentasDTO>> getProductosMasVendidos(
            @RequestParam("desde") String desde,
            @RequestParam("hasta") String hasta) {

        List<ProductoVentasDTO> lista = reportesVentasService.obtenerProductosMasVendidos(desde, hasta);
        return ResponseEntity.ok(lista);
    }

    @GetMapping("/clientes")
    public ResponseEntity<List<ClienteFrecuenteDTO>> getClientesFrecuentes(
            @RequestParam("desde") String desde,
            @RequestParam("hasta") String hasta) {

        List<ClienteFrecuenteDTO> lista = reportesVentasService.obtenerClientesFrecuentes(desde, hasta);
        return ResponseEntity.ok(lista);
    }

    @GetMapping("/habitaciones")
    public ResponseEntity<List<HabitacionVentasDTO>> getHabitacionesMasVendidas(
            @RequestParam("desde") String desde,
            @RequestParam("hasta") String hasta) {

        List<HabitacionVentasDTO> lista = reportesVentasService.obtenerHabitacionesMasVendidas(desde, hasta);
        return ResponseEntity.ok(lista);
    }

    @GetMapping("/salones")
    public ResponseEntity<List<SalonVentasDTO>> getSalonesMasVendidos(
            @RequestParam("desde") String desde,
            @RequestParam("hasta") String hasta) {

        List<SalonVentasDTO> lista = reportesVentasService.obtenerSalonesMasVendidos(desde, hasta);
        return ResponseEntity.ok(lista);
    }

    @GetMapping("/metodos-pago")
    public ResponseEntity<List<PagoVentasDTO>> getMetodosPago(
            @RequestParam("desde") String desde,
            @RequestParam("hasta") String hasta) {

        List<PagoVentasDTO> lista = reportesVentasService.obtenerMetodosPago(desde, hasta);
        return ResponseEntity.ok(lista);
    }

    @GetMapping("/{tipo}/pdf")
    public ResponseEntity<byte[]> descargarPdf(
            @PathVariable("tipo") String tipo,
            @RequestParam("desde") String desde,
            @RequestParam("hasta") String hasta) {

        byte[] contenido = reportesVentasService.generarPdf(tipo, desde, hasta);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_PDF);
        headers.setContentDispositionFormData(
            "attachment",
            String.format("reporte_%s_%s_a_%s.pdf", tipo, desde, hasta)
        );

        return new ResponseEntity<>(contenido, headers, HttpStatus.OK);
    }

    @GetMapping("/{tipo}/excel")
    public ResponseEntity<byte[]> descargarExcel(
            @PathVariable("tipo") String tipo,
            @RequestParam("desde") String desde,
            @RequestParam("hasta") String hasta) {

        byte[] contenido = reportesVentasService.generarExcel(tipo, desde, hasta);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(
            MediaType.parseMediaType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet")
        );
        headers.setContentDispositionFormData(
            "attachment",
            String.format("reporte_%s_%s_a_%s.xlsx", tipo, desde, hasta)
        );

        return new ResponseEntity<>(contenido, headers, HttpStatus.OK);
    }

}
