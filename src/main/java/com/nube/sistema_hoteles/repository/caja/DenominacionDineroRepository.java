package com.nube.sistema_hoteles.repository.caja;

import org.springframework.data.jpa.repository.JpaRepository;

import com.nube.sistema_hoteles.entity.caja.DenominacionDinero;
import com.nube.sistema_hoteles.entity.caja.TipoDenominacion;

import java.util.List;

public interface DenominacionDineroRepository extends JpaRepository<DenominacionDinero, Integer> {
    List<DenominacionDinero> findByTipo(TipoDenominacion tipo);
}
