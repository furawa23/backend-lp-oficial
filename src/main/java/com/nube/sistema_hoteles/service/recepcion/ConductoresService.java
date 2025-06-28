package com.nube.sistema_hoteles.service.recepcion;

import java.util.List;
import java.util.Optional;

import com.nube.sistema_hoteles.entity.recepcion.Conductores;

public interface ConductoresService {

    List<Conductores> buscarTodos();
    
    Conductores guardar(Conductores conductor);

    Optional<Conductores> buscarId(Integer id);

    Conductores modificar(Conductores conductor);

    void eliminar(Integer id);
}
