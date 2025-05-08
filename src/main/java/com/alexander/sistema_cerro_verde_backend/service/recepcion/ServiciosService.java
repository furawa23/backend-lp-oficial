package com.alexander.sistema_cerro_verde_backend.service.recepcion;

import java.util.List;
import java.util.Optional;

import com.alexander.sistema_cerro_verde_backend.entity.recepcion.Servicios;

public interface ServiciosService {

    List<Servicios> buscarTodos();
    
    Servicios guardar(Servicios servicio);

    Optional<Servicios> buscarId(Integer id);

    void eliminarId(Integer id);
}
