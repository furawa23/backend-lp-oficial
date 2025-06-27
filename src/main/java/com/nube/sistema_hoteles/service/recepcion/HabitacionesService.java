package com.nube.sistema_hoteles.service.recepcion;

import java.util.List;
import java.util.Optional;

import com.nube.sistema_hoteles.entity.recepcion.Habitaciones;


public interface HabitacionesService {

    List<Habitaciones> buscarTodos();
    
    Habitaciones guardar(Habitaciones habitacion);

    Optional<Habitaciones> buscarId(Integer id);

    Habitaciones modificar(Habitaciones habitacion);

    void eliminar(Integer id);
}
