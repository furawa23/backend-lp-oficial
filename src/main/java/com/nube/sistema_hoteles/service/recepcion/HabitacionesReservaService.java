package com.nube.sistema_hoteles.service.recepcion;

import java.util.List;
import java.util.Optional;

import com.nube.sistema_hoteles.entity.recepcion.HabitacionesXReserva;

public interface HabitacionesReservaService {

    List<HabitacionesXReserva> buscarTodos();
    
    HabitacionesXReserva guardar(HabitacionesXReserva reservahabitacion);

    Optional<HabitacionesXReserva> buscarId(Integer id);

    HabitacionesXReserva modificar(HabitacionesXReserva habreserva);
    
    void eliminar(Integer id);

}
