package com.nube.sistema_hoteles.service.recepcion;

import java.util.List;
import java.util.Optional;

import com.nube.sistema_hoteles.entity.recepcion.Pisos;

public interface PisosService {

    List<Pisos> buscarTodos();
    
    Pisos guardar(Pisos piso);

    Optional<Pisos> buscarId(Integer id);

    void eliminar(Integer id);
}
