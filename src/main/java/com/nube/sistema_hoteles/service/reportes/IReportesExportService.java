package com.nube.sistema_hoteles.service.reportes;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.util.List;

import com.nube.sistema_hoteles.entity.reportes.ProductoReporteDTO;
import com.nube.sistema_hoteles.entity.reportes.ProveedorReporteDTO;

public interface IReportesExportService {

    ByteArrayInputStream generarPdfProductos(List<ProductoReporteDTO> productos);

    ByteArrayOutputStream generarExcelProductos(List<ProductoReporteDTO> productos);

    ByteArrayInputStream generarPdfProveedores(List<ProveedorReporteDTO> proveedores);

    ByteArrayOutputStream generarExcelProveedores(List<ProveedorReporteDTO> proveedores);
}