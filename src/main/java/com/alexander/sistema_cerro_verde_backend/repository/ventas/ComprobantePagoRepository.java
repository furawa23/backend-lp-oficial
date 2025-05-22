package com.alexander.sistema_cerro_verde_backend.repository.ventas;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.alexander.sistema_cerro_verde_backend.entity.ventas.ComprobantePago;

public interface ComprobantePagoRepository extends JpaRepository<ComprobantePago, Integer> {

    @Query("SELECT cp FROM ComprobantePago cp WHERE cp.numComprobante = :serie ORDER BY cp.id DESC")
    List<ComprobantePago> findUltimoPorSerie(@Param("serie") String serie, Pageable pageable);

    
}
