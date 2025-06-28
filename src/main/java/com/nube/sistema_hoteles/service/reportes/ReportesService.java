package com.nube.sistema_hoteles.service.reportes;

import java.util.List;

import org.springframework.stereotype.Service;

import com.nube.sistema_hoteles.entity.reportes.ProductoReporteDTO;
import com.nube.sistema_hoteles.entity.reportes.ProveedorReporteDTO;
import com.nube.sistema_hoteles.repository.compras.ComprasRepository;

@Service
public class ReportesService {
private final ComprasRepository compraRepository;

    public ReportesService(ComprasRepository compraRepository) {
        this.compraRepository = compraRepository;
    }

    // ← Ahora con stockFilter
    public List<ProductoReporteDTO> obtenerProductosMasComprados(
            String desde,
            String hasta,
            String stockFilter   // ← nuevo parámetro
    ) {
        return compraRepository.findProductosMasCompradosNative(
            desde,
            hasta,
            stockFilter        // ← lo pasamos al repo
        );
    }

    public List<ProveedorReporteDTO> obtenerProveedoresMasComprados(String desde, String hasta) {
        return compraRepository.findProveedoresMasCompradosNative(desde, hasta);
    }
}