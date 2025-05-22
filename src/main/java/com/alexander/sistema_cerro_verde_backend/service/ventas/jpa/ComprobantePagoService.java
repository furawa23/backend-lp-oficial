package com.alexander.sistema_cerro_verde_backend.service.ventas.jpa;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.alexander.sistema_cerro_verde_backend.entity.ventas.ComprobantePago;
import com.alexander.sistema_cerro_verde_backend.repository.ventas.ComprobantePagoRepository;
import com.alexander.sistema_cerro_verde_backend.service.ventas.IComprobantePagoService;

@Service
public class ComprobantePagoService implements IComprobantePagoService {

    @Autowired
    private ComprobantePagoRepository repoComprobante;

    @Override
    public List<ComprobantePago> buscarTodos() {
        return repoComprobante.findAll();
    }

    @Override
    public Optional<ComprobantePago> buscarPorId(Integer id) {
        return repoComprobante.findById(id);
    }

    @Override
    public void guardar(ComprobantePago comprobante) {
        repoComprobante.save(comprobante);
    }

    @Override
    public void modificar(ComprobantePago comprobante) {
        repoComprobante.save(comprobante);
    }

    @Override
    public void eliminar(Integer id) {
        repoComprobante.deleteById(id);
    }

    @Override
    public ComprobantePago guardarConSerie(ComprobantePago comprobante, String tipoComprobante) {
        String serie;
        String nuevoCorrelativo;

        if (tipoComprobante.equalsIgnoreCase("BOLETA")) {
            serie = "B001";
        } else if (tipoComprobante.equalsIgnoreCase("FACTURA")) {
            serie = "F001";
        } else {
            throw new RuntimeException("Tipo de comprobante inválido");
        }

        Pageable topOne = PageRequest.of(0, 1);
        List<ComprobantePago> ultimo = repoComprobante.findUltimoPorSerie(serie, topOne);

        int ultimoCorrelativo = 0;
        if (!ultimo.isEmpty()) {
            String serieAnterior = tipoComprobante.equals("BOLETA")
                    ? ultimo.get(0).getNumSerieBoleta()
                    : ultimo.get(0).getNumSerieFactura();
            ultimoCorrelativo = Integer.parseInt(serieAnterior);
        }

        int nuevo = ultimoCorrelativo + 1;
        nuevoCorrelativo = String.format("%08d", nuevo);

        comprobante.setNumComprobante(serie);
        if (tipoComprobante.equals("BOLETA")) {
            comprobante.setNumSerieBoleta(nuevoCorrelativo);
        } else {
            comprobante.setNumSerieFactura(nuevoCorrelativo);
        }

        return repoComprobante.save(comprobante);
    }
}
