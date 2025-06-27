package com.nube.sistema_hoteles.service.recepcion;

import java.util.List;
import java.util.Optional;

import com.nube.sistema_hoteles.entity.recepcion.Recojo;

public interface RecojoService {

    List<Recojo> buscarTodos();
    
    Recojo guardar(Recojo recojo);

    Optional<Recojo> buscarId(Integer id);

    Recojo modificar(Recojo recojo);

    void eliminar(Integer id);
}
