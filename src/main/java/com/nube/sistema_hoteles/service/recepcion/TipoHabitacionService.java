package com.nube.sistema_hoteles.service.recepcion;

import java.util.List;
import java.util.Optional;

import com.nube.sistema_hoteles.entity.recepcion.TipoHabitacion;

public interface TipoHabitacionService {

    List<TipoHabitacion> buscarTodos();
    
    TipoHabitacion guardar(TipoHabitacion tipo);

    Optional<TipoHabitacion> buscarId(Integer id);

    TipoHabitacion modificar(TipoHabitacion tipo);

    void eliminar(Integer id);
}
