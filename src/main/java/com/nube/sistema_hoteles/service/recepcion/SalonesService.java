package com.nube.sistema_hoteles.service.recepcion;

import java.util.List;
import java.util.Optional;

import com.nube.sistema_hoteles.entity.recepcion.Salones;

public interface SalonesService {

    List<Salones> buscarTodos();
    
    Salones guardar(Salones salon);

    Optional<Salones> buscarId(Integer id);

    Salones modificar(Salones salon);


    void eliminar(Integer id);
}
