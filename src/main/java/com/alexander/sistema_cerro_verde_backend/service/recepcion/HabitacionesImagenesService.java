package com.alexander.sistema_cerro_verde_backend.service.recepcion;

import java.util.List;
import java.util.Optional;

import com.alexander.sistema_cerro_verde_backend.entity.recepcion.HabitacionesXImagenes;

public interface HabitacionesImagenesService {

    List<HabitacionesXImagenes> buscarTodos();
    
    HabitacionesXImagenes guardar(HabitacionesXImagenes habitacion);

    Optional<HabitacionesXImagenes> buscarId(Integer id);

    void eliminar(Integer id);

    void modificar(HabitacionesXImagenes habimg);
}
