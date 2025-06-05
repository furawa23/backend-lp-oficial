package com.alexander.sistema_cerro_verde_backend.service.reportes.jpa;

import java.io.ByteArrayOutputStream;
import java.util.List;

import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;

import com.alexander.sistema_cerro_verde_backend.entity.reportes.ClienteFrecuenteDTO;
import com.alexander.sistema_cerro_verde_backend.entity.reportes.HabitacionVentasDTO;
import com.alexander.sistema_cerro_verde_backend.entity.reportes.PagoVentasDTO;
import com.alexander.sistema_cerro_verde_backend.entity.reportes.ProductoVentasDTO;
import com.alexander.sistema_cerro_verde_backend.entity.reportes.SalonVentasDTO;
import com.alexander.sistema_cerro_verde_backend.entity.reportes.VentaResumenDTO;
import com.alexander.sistema_cerro_verde_backend.repository.ventas.VentasRepository;
import com.itextpdf.io.font.constants.StandardFonts;
import com.itextpdf.kernel.colors.ColorConstants;
import com.itextpdf.kernel.font.PdfFont;
import com.itextpdf.kernel.font.PdfFontFactory;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Cell;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;
import com.itextpdf.layout.properties.TextAlignment;
import com.itextpdf.layout.properties.UnitValue;

@Service
public class ReportesVentasService {

    private final VentasRepository ventasRepo;

    public ReportesVentasService(VentasRepository ventasRepo) {
        this.ventasRepo = ventasRepo;
    }

    /**
     * Retorna la lista de productos más vendidos en el rango [desde, hasta].
     * @param desde Fecha de inicio en formato "YYYY-MM-DD"
     * @param hasta Fecha de fin en formato "YYYY-MM-DD"
     */
    public List<ProductoVentasDTO> obtenerProductosMasVendidos(String desde, String hasta) {
        return ventasRepo.findProductosMasVendidos(desde, hasta);
    }

    /**
     * Retorna la lista de clientes más frecuentes en el rango [desde, hasta].
     * @param desde Fecha de inicio en formato "YYYY-MM-DD"
     * @param hasta Fecha de fin en formato "YYYY-MM-DD"
     */
    public List<ClienteFrecuenteDTO> obtenerClientesFrecuentes(String desde, String hasta) {
        return ventasRepo.findClientesFrecuentes(desde, hasta);
    }

    /**
     * Retorna la lista de habitaciones más vendidas en el rango [desde, hasta].
     * @param desde Fecha de inicio en formato "YYYY-MM-DD"
     * @param hasta Fecha de fin en formato "YYYY-MM-DD"
     */
    public List<HabitacionVentasDTO> obtenerHabitacionesMasVendidas(String desde, String hasta) {
        return ventasRepo.findHabitacionesMasVendidas(desde, hasta);
    }

    /**
     * Retorna la lista de salones más vendidos (o alquilados) en el rango [desde, hasta].
     * @param desde Fecha de inicio en formato "YYYY-MM-DD"
     * @param hasta Fecha de fin en formato "YYYY-MM-DD"
     */
    public List<SalonVentasDTO> obtenerSalonesMasVendidos(String desde, String hasta) {
        return ventasRepo.findSalonesMasVendidos(desde, hasta);
    }

    /**
     * Retorna la lista de métodos de pago más usados en el rango [desde, hasta].
     * @param desde Fecha de inicio en formato "YYYY-MM-DD"
     * @param hasta Fecha de fin en formato "YYYY-MM-DD"
     */
    public List<PagoVentasDTO> obtenerMetodosPago(String desde, String hasta) {
        return ventasRepo.findMetodosPago(desde, hasta);
    }


    private List<VentaResumenDTO> obtenerDatosResumen(String tipo, String desde, String hasta) {
        switch (tipo) {
            case "productos":
                return ventasRepo.findProductosMasVendidosResumen(desde, hasta);
            case "salones":
                return ventasRepo.findSalonesMasVendidosResumen(desde, hasta);
            case "habitaciones":
                return ventasRepo.findHabitacionesMasVendidasResumen(desde, hasta);
            case "clientes":
                return ventasRepo.findClientesFrecuentesResumen(desde, hasta);
            case "metodoPago":
                return ventasRepo.findMetodosPagoResumen(desde, hasta);
            default:
                throw new IllegalArgumentException("Tipo de reporte inválido: " + tipo);
        }
    }

    /**
     * Genera un PDF en memoria (byte[]) con el reporte del 'tipo' especificado
     * dentro del rango [desde, hasta]. El contenido se basa en VentaResumenDTO.
     *
     * @param tipo  "productos", "salones", "habitaciones", "clientes" o "metodoPago"
     * @param desde Fecha de inicio en formato "YYYY-MM-DD"
     * @param hasta Fecha de fin en formato "YYYY-MM-DD"
     * @return Arreglo de bytes que representa el PDF generado.
     */
    public byte[] generarPdf(String tipo, String desde, String hasta) {
        try {
            // 1) Obtengo la lista de datos (DTO) para el tipo de reporte
            List<VentaResumenDTO> lista = obtenerDatosResumen(tipo, desde, hasta);

            // 2) Creo un stream en memoria para almacenar el PDF
            ByteArrayOutputStream baos = new ByteArrayOutputStream();

            // 3) Instancio PdfWriter, PdfDocument y Documento iText
            PdfWriter writer = new PdfWriter(baos);
            PdfDocument pdfDoc = new PdfDocument(writer);
            Document document = new Document(pdfDoc);

            // 4) Cargo la fuente Helvetica
            PdfFont font = PdfFontFactory.createFont(StandardFonts.HELVETICA);

            // 5) Pongo un título dinámico dependiendo del 'tipo'
            String titulo;
            switch (tipo) {
                case "productos":   titulo = "Productos Más Vendidos";       break;
                case "salones":     titulo = "Salones Más Vendidos";         break;
                case "habitaciones":titulo = "Habitaciones Más Vendidas";    break;
                case "clientes":    titulo = "Clientes Más Frecuentes";      break;
                case "metodoPago":  titulo = "Métodos de Pago Más Usados";   break;
                default:            titulo = "Reporte";                       break;
            }

            Paragraph header = new Paragraph(titulo)
                .setFont(font)
                .setBold()
                .setFontSize(14)
                .setTextAlignment(TextAlignment.CENTER);

            document.add(header);
            document.add(new Paragraph("\n")); // salto de línea

            // 6) Creo la tabla con 3 columnas: Nombre | Cantidad | Total
            float[] columnWidths = {200F, 100F, 100F};
            Table table = new Table(columnWidths)
                .setWidth(UnitValue.createPercentValue(100));

            // 7) Encabezados de columna (fondo gris)
            Cell h1 = new Cell().add(new Paragraph("Nombre").setFont(font).setBold())
                                 .setBackgroundColor(ColorConstants.LIGHT_GRAY)
                                 .setTextAlignment(TextAlignment.CENTER);
            Cell h2 = new Cell().add(new Paragraph("Cantidad").setFont(font).setBold())
                                 .setBackgroundColor(ColorConstants.LIGHT_GRAY)
                                 .setTextAlignment(TextAlignment.CENTER);
            Cell h3 = new Cell().add(new Paragraph("Total (S/.)").setFont(font).setBold())
                                 .setBackgroundColor(ColorConstants.LIGHT_GRAY)
                                 .setTextAlignment(TextAlignment.CENTER);

            table.addHeaderCell(h1);
            table.addHeaderCell(h2);
            table.addHeaderCell(h3);

            // 8) Relleno cada fila con los datos de lista
            for (VentaResumenDTO dto : lista) {
                Cell cNombre = new Cell().add(new Paragraph(dto.getNombre()).setFont(font))
                                         .setTextAlignment(TextAlignment.LEFT);
                Cell cCant   = new Cell().add(new Paragraph(dto.getCantidad().toString()).setFont(font))
                                         .setTextAlignment(TextAlignment.CENTER);
                Cell cTot    = new Cell().add(new Paragraph(String.format("%.2f", dto.getTotal())).setFont(font))
                                         .setTextAlignment(TextAlignment.RIGHT);

                table.addCell(cNombre);
                table.addCell(cCant);
                table.addCell(cTot);
            }

            // 9) Agrego la tabla al documento y lo cierro
            document.add(table);
            document.close();

            return baos.toByteArray();
        } catch (Exception e) {
            throw new RuntimeException("Error al generar PDF: " + e.getMessage(), e);
        }
    }


    // ------------------------------------------------------------
    //   GENERAR EXCEL (con Apache POI)
    // ------------------------------------------------------------

    /**
     * Genera un archivo Excel (.xlsx) en memoria (byte[]) con el reporte del 'tipo'
     * especificado dentro del rango [desde, hasta]. El contenido se basa en VentaResumenDTO.
     *
     * @param tipo  "productos", "salones", "habitaciones", "clientes" o "metodoPago"
     * @param desde Fecha de inicio en formato "YYYY-MM-DD"
     * @param hasta Fecha de fin en formato "YYYY-MM-DD"
     * @return Arreglo de bytes que representa el archivo Excel generado.
     */
    public byte[] generarExcel(String tipo, String desde, String hasta) {
        try {
            // 1) Obtengo la lista de datos (DTO) para el tipo de reporte
            List<VentaResumenDTO> lista = obtenerDatosResumen(tipo, desde, hasta);

            // 2) Creo el Workbook y la hoja
            Workbook workbook = new XSSFWorkbook();
            Sheet sheet = workbook.createSheet("Reporte");

            // 3) Defino un estilo centrado y en negrita para los encabezados
            CellStyle headerStyle = workbook.createCellStyle();
            headerStyle.setAlignment(HorizontalAlignment.CENTER);
            org.apache.poi.ss.usermodel.Font font = workbook.createFont();
            font.setBold(true);
            headerStyle.setFont(font);

            // 4) Fila de encabezado (fila 0)
            Row headerRow = sheet.createRow(0);
            org.apache.poi.ss.usermodel.Cell cell0 = headerRow.createCell(0);
            cell0.setCellValue("Nombre");
            cell0.setCellStyle(headerStyle);

            org.apache.poi.ss.usermodel.Cell cell1 = headerRow.createCell(1);
            cell1.setCellValue("Cantidad");
            cell1.setCellStyle(headerStyle);

            org.apache.poi.ss.usermodel.Cell cell2 = headerRow.createCell(2);
            cell2.setCellValue("Total (S/.)");
            cell2.setCellStyle(headerStyle);

            // 5) Relleno cada fila con los datos de lista
            int rowIdx = 1;
            for (VentaResumenDTO dto : lista) {
                Row row = sheet.createRow(rowIdx++);

                org.apache.poi.ss.usermodel.Cell c0 = row.createCell(0);
                c0.setCellValue(dto.getNombre());

                org.apache.poi.ss.usermodel.Cell c1 = row.createCell(1);
                c1.setCellValue(dto.getCantidad());

                org.apache.poi.ss.usermodel.Cell c2 = row.createCell(2);
                c2.setCellValue(dto.getTotal());
            }

            // 6) Ajusto ancho de columnas automáticamente
            sheet.autoSizeColumn(0);
            sheet.autoSizeColumn(1);
            sheet.autoSizeColumn(2);

            // 7) Escribo el contenido en un ByteArrayOutputStream y cierro el workbook
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            workbook.write(baos);
            workbook.close();

            return baos.toByteArray();
        } catch (Exception e) {
            throw new RuntimeException("Error al generar Excel: " + e.getMessage(), e);
        }
    }
}
