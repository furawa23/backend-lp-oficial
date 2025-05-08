package com.alexander.sistema_cerro_verde_backend.service.recepcion;

import java.util.List;
import java.util.Optional;

import com.alexander.sistema_cerro_verde_backend.entity.recepcion.SalonesXImagenes;

public interface SalonesImagenesService {

    List<SalonesXImagenes> buscarTodos();
    
    SalonesXImagenes guardar(SalonesXImagenes salonimagen);

    Optional<SalonesXImagenes> buscarId(Integer id);

    void eliminarId(Integer id);
}
