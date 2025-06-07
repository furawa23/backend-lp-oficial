package com.alexander.sistema_cerro_verde_backend.controller.reportes;

import java.io.ByteArrayInputStream;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.List;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.alexander.sistema_cerro_verde_backend.entity.reportes.ProductoReporteDTO;
import com.alexander.sistema_cerro_verde_backend.entity.reportes.ProveedorReporteDTO;
import com.alexander.sistema_cerro_verde_backend.service.reportes.ReportesService;
import com.alexander.sistema_cerro_verde_backend.service.reportes.jpa.ReportesExportService;


@RestController
@RequestMapping("/cerro-verde/reportes")
@CrossOrigin("*")
public class ReportesController {

    private final ReportesService reportesService;
    private final ReportesExportService reportesExportService;

    public ReportesController(ReportesService reportesService,
                              ReportesExportService reportesExportService) {
        this.reportesService = reportesService;
        this.reportesExportService = reportesExportService;
    }

    @GetMapping("/productos")
    public List<ProductoReporteDTO> getProductosReporte(
            @RequestParam String desde,
            @RequestParam String hasta) {
        return reportesService.obtenerProductosMasComprados(desde, hasta);
    }

    @GetMapping("/proveedores")
    public List<ProveedorReporteDTO> getProveedoresReporte(
            @RequestParam String desde,
            @RequestParam String hasta) {
        return reportesService.obtenerProveedoresMasComprados(desde, hasta);
    }

    // ——— Endpoint PDF Productos ——————————————————————————————————————————
    @GetMapping("/productos/pdf")
    public ResponseEntity<byte[]> downloadPdfProductos(
            @RequestParam String desde,
            @RequestParam String hasta) {

        // 1) Obtener la lista de DTOs
        List<ProductoReporteDTO> lista = reportesService.obtenerProductosMasComprados(desde, hasta);

        // 2) Generar el PDF (ByteArrayInputStream)
        ByteArrayInputStream bis = reportesExportService.generarPdfProductos(lista);
        byte[] pdfBytes = bis.readAllBytes();

        // 3) Devolver el PDF como attachment
        String filename = "productos_reporte_" + desde + "_a_" + hasta + ".pdf";
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_PDF);
        // codificar el nombre para que no haya problemas de espacios o caracteres especiales
        String encodedFilename = URLEncoder.encode(filename, StandardCharsets.UTF_8);
        headers.set(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + encodedFilename + "\"");
        return new ResponseEntity<>(pdfBytes, headers, HttpStatus.OK);
    }

    // ——— Endpoint Excel Productos —————————————————————————————————————————
    @GetMapping("/productos/excel")
    public ResponseEntity<byte[]> downloadExcelProductos(
            @RequestParam String desde,
            @RequestParam String hasta) {

        List<ProductoReporteDTO> lista = reportesService.obtenerProductosMasComprados(desde, hasta);
        java.io.ByteArrayOutputStream baos = reportesExportService.generarExcelProductos(lista);
        byte[] excelBytes = baos.toByteArray();

        String filename = "productos_reporte_" + desde + "_a_" + hasta + ".xlsx";
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.parseMediaType(
                "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"));
        String encodedFilename = URLEncoder.encode(filename, StandardCharsets.UTF_8);
        headers.set(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + encodedFilename + "\"");
        return new ResponseEntity<>(excelBytes, headers, HttpStatus.OK);
    }

    // ——— Endpoint PDF Proveedores ——————————————————————————————————————————
    @GetMapping("/proveedores/pdf")
    public ResponseEntity<byte[]> downloadPdfProveedores(
            @RequestParam String desde,
            @RequestParam String hasta) {

        List<ProveedorReporteDTO> lista = reportesService.obtenerProveedoresMasComprados(desde, hasta);
        ByteArrayInputStream bis = reportesExportService.generarPdfProveedores(lista);
        byte[] pdfBytes = bis.readAllBytes();

        String filename = "proveedores_reporte_" + desde + "_a_" + hasta + ".pdf";
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_PDF);
        String encodedFilename = URLEncoder.encode(filename, StandardCharsets.UTF_8);
        headers.set(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + encodedFilename + "\"");
        return new ResponseEntity<>(pdfBytes, headers, HttpStatus.OK);
    }

    // ——— Endpoint Excel Proveedores —————————————————————————————————————————
    @GetMapping("/proveedores/excel")
    public ResponseEntity<byte[]> downloadExcelProveedores(
            @RequestParam String desde,
            @RequestParam String hasta) {

        List<ProveedorReporteDTO> lista = reportesService.obtenerProveedoresMasComprados(desde, hasta);
        java.io.ByteArrayOutputStream baos = reportesExportService.generarExcelProveedores(lista);
        byte[] excelBytes = baos.toByteArray();

        String filename = "proveedores_reporte_" + desde + "_a_" + hasta + ".xlsx";
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.parseMediaType(
                "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"));
        String encodedFilename = URLEncoder.encode(filename, StandardCharsets.UTF_8);
        headers.set(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + encodedFilename + "\"");
        return new ResponseEntity<>(excelBytes, headers, HttpStatus.OK);
    }
}