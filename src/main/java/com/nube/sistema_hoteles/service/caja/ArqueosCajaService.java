package com.nube.sistema_hoteles.service.caja;

import java.util.List;
import java.util.Optional;

import com.nube.sistema_hoteles.entity.caja.ArqueosCaja;
import com.nube.sistema_hoteles.entity.caja.Cajas;

public interface ArqueosCajaService {
    
    List<ArqueosCaja> buscarTodos();
    
    ArqueosCaja guardar(ArqueosCaja arqueo);

    Optional<ArqueosCaja> buscarId(Integer id);

    void eliminarId(Integer id);

    Optional<ArqueosCaja> buscarPorCaja(Cajas caja);

    List<ArqueosCaja> buscarTodosPorCaja(Cajas caja);

}
