package com.alexander.sistema_cerro_verde_backend.service.reportes.jpa;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.util.List;

import org.springframework.stereotype.Service;

import com.alexander.sistema_cerro_verde_backend.entity.reportes.ProductoReporteDTO;
import com.alexander.sistema_cerro_verde_backend.entity.reportes.ProveedorReporteDTO;

// iText 7 para PDF
import com.itextpdf.io.font.constants.StandardFonts;
import com.itextpdf.kernel.font.PdfFontFactory;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Cell;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;
import com.itextpdf.layout.properties.TextAlignment;  // ← paquete correcto


import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.alexander.sistema_cerro_verde_backend.service.reportes.IReportesExportService;

@Service
public class ReportesExportService implements IReportesExportService{
    // ——— PDF Productos ——————————————————————————————————————————
    @Override
    public ByteArrayInputStream generarPdfProductos(List<ProductoReporteDTO> productos) {
        try {
            // 1) Creamos el stream de salida en memoria
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            PdfWriter writer = new PdfWriter(baos);
            PdfDocument pdfDoc = new PdfDocument(writer);
            Document document = new Document(pdfDoc);

            // 2) Título
            Paragraph title = new Paragraph("Reporte de Productos Más Comprados")
                    .setFont(PdfFontFactory.createFont(StandardFonts.HELVETICA_BOLD))
                    .setFontSize(16)
                    .setTextAlignment(TextAlignment.CENTER);
            document.add(title);
            document.add(new Paragraph("\n"));

            // 3) Tabla con 3 columnas: Producto | Cantidad | Total Gastado
            float[] columnWidths = {200F, 100F, 100F};
            Table table = new Table(columnWidths);

            // Encabezados
            table.addHeaderCell(new Cell().add(new Paragraph("Producto").setBold()));
            table.addHeaderCell(new Cell().add(new Paragraph("Cantidad").setBold()));
            table.addHeaderCell(new Cell().add(new Paragraph("Total Gastado").setBold()));

            // 4) Filas con datos
            for (ProductoReporteDTO pr : productos) {
                table.addCell(new Cell().add(new Paragraph(pr.getProductoNombre())));
                table.addCell(new Cell().add(new Paragraph(pr.getCantidadComprada().toString())));
                table.addCell(new Cell().add(new Paragraph(pr.getTotalGastado().toString())));
            }

            document.add(table);
            document.close();

            return new ByteArrayInputStream(baos.toByteArray());
        } catch (Exception e) {
            throw new RuntimeException("Error generando PDF de Productos", e);
        }
    }

    // ——— Excel Productos ——————————————————————————————————————————
    @Override
    public ByteArrayOutputStream generarExcelProductos(List<ProductoReporteDTO> productos) {
        try (Workbook workbook = new XSSFWorkbook()) {
            Sheet sheet = workbook.createSheet("Productos Más Comprados");

            // Estilo para encabezado centrado
            CellStyle headerStyle = workbook.createCellStyle();
            headerStyle.setAlignment(HorizontalAlignment.CENTER);

            // Fila 0: encabezados
            Row headerRow = sheet.createRow(0);
            org.apache.poi.ss.usermodel.Cell cell0 = headerRow.createCell(0);
            cell0.setCellValue("Producto");
            cell0.setCellStyle(headerStyle);

            org.apache.poi.ss.usermodel.Cell cell1 = headerRow.createCell(1);
            cell1.setCellValue("Cantidad");
            cell1.setCellStyle(headerStyle);

            org.apache.poi.ss.usermodel.Cell cell2 = headerRow.createCell(2);
            cell2.setCellValue("Total Gastado");
            cell2.setCellStyle(headerStyle);

            // Filas de datos
            int rowIdx = 1;
            for (ProductoReporteDTO pr : productos) {
                Row row = sheet.createRow(rowIdx++);
                row.createCell(0).setCellValue(pr.getProductoNombre());
                row.createCell(1).setCellValue(pr.getCantidadComprada());
                row.createCell(2).setCellValue(pr.getTotalGastado().doubleValue());
            }

            // Auto‐size
            sheet.autoSizeColumn(0);
            sheet.autoSizeColumn(1);
            sheet.autoSizeColumn(2);

            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            workbook.write(baos);
            return baos;
        } catch (Exception e) {
            throw new RuntimeException("Error generando Excel de Productos", e);
        }
    }

    // ——— PDF Proveedores ——————————————————————————————————————————
    @Override
    public ByteArrayInputStream generarPdfProveedores(List<ProveedorReporteDTO> proveedores) {
        try {
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            PdfWriter writer = new PdfWriter(baos);
            PdfDocument pdfDoc = new PdfDocument(writer);
            Document document = new Document(pdfDoc);

            Paragraph title = new Paragraph("Reporte de Proveedores Más Comprados")
                    .setFont(PdfFontFactory.createFont(StandardFonts.HELVETICA_BOLD))
                    .setFontSize(16)
                    .setTextAlignment(TextAlignment.CENTER);
            document.add(title);
            document.add(new Paragraph("\n"));

            float[] columnWidths = {200F, 100F, 100F};
            Table table = new Table(columnWidths);

            table.addHeaderCell(new Cell().add(new Paragraph("Proveedor").setBold()));
            table.addHeaderCell(new Cell().add(new Paragraph("Cant. Facturas").setBold()));
            table.addHeaderCell(new Cell().add(new Paragraph("Total Gastado").setBold()));

            for (ProveedorReporteDTO pr : proveedores) {
                table.addCell(new Cell().add(new Paragraph(pr.getProveedorNombre())));
                table.addCell(new Cell().add(new Paragraph(pr.getCantidadFacturas().toString())));
                table.addCell(new Cell().add(new Paragraph(pr.getTotalGastado().toString())));
            }

            document.add(table);
            document.close();
            return new ByteArrayInputStream(baos.toByteArray());
        } catch (Exception e) {
            throw new RuntimeException("Error generando PDF de Proveedores", e);
        }
    }

    // ——— Excel Proveedores ——————————————————————————————————————————
    @Override
    public ByteArrayOutputStream generarExcelProveedores(List<ProveedorReporteDTO> proveedores) {
        try (Workbook workbook = new XSSFWorkbook()) {
            Sheet sheet = workbook.createSheet("Proveedores Más Comprados");

            CellStyle headerStyle = workbook.createCellStyle();
            headerStyle.setAlignment(HorizontalAlignment.CENTER);

            Row headerRow = sheet.createRow(0);
            org.apache.poi.ss.usermodel.Cell cell0 = headerRow.createCell(0);
            cell0.setCellValue("Proveedor");
            cell0.setCellStyle(headerStyle);

            org.apache.poi.ss.usermodel.Cell cell1 = headerRow.createCell(1);
            cell1.setCellValue("Cant. Facturas");
            cell1.setCellStyle(headerStyle);

            org.apache.poi.ss.usermodel.Cell cell2 = headerRow.createCell(2);
            cell2.setCellValue("Total Gastado");
            cell2.setCellStyle(headerStyle);

            int rowIdx = 1;
            for (ProveedorReporteDTO pr : proveedores) {
                Row row = sheet.createRow(rowIdx++);
                row.createCell(0).setCellValue(pr.getProveedorNombre());
                row.createCell(1).setCellValue(pr.getCantidadFacturas());
                row.createCell(2).setCellValue(pr.getTotalGastado());
            }

            sheet.autoSizeColumn(0);
            sheet.autoSizeColumn(1);
            sheet.autoSizeColumn(2);

            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            workbook.write(baos);
            return baos;
        } catch (Exception e) {
            throw new RuntimeException("Error generando Excel de Proveedores", e);
        }
    }
}