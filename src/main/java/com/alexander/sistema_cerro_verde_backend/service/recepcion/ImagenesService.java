package com.alexander.sistema_cerro_verde_backend.service.recepcion;

import java.util.List;
import java.util.Optional;

import com.alexander.sistema_cerro_verde_backend.entity.recepcion.Imagenes;


public interface ImagenesService {

    List<Imagenes> buscarTodos();
    
    Imagenes guardar(Imagenes imagen);

    Optional<Imagenes> buscarId(Integer id);

    void eliminar(Integer id);
    void modificar(Imagenes imagen);
}
