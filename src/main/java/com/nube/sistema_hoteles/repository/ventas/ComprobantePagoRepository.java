package com.nube.sistema_hoteles.repository.ventas;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.nube.sistema_hoteles.entity.ventas.ComprobantePago;

public interface ComprobantePagoRepository extends JpaRepository<ComprobantePago, Integer> {

    Optional<ComprobantePago> findTopByNumComprobanteOrderByIdVentaDesc(String numComprobante);
}
