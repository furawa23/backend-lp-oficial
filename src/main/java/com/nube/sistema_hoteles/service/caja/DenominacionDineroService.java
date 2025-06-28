package com.nube.sistema_hoteles.service.caja;

import java.util.List;
import java.util.Optional;

import com.nube.sistema_hoteles.entity.caja.DenominacionDinero;
import com.nube.sistema_hoteles.entity.caja.TipoDenominacion;

public interface DenominacionDineroService {

    List<DenominacionDinero> buscarTodos();

    Optional<DenominacionDinero> buscarId(Integer id);

    List<DenominacionDinero> buscarPorTipo(TipoDenominacion tipo);
    
}
