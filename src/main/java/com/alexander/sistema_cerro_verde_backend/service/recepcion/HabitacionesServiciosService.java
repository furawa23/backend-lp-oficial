package com.alexander.sistema_cerro_verde_backend.service.recepcion;

import java.util.List;
import java.util.Optional;

import com.alexander.sistema_cerro_verde_backend.entity.recepcion.HabitacionesXServicios;

public interface HabitacionesServiciosService {

    List<HabitacionesXServicios> buscarTodos();
    
    HabitacionesXServicios guardar(HabitacionesXServicios habitacionservicio);

    Optional<HabitacionesXServicios> buscarId(Integer id);

    void eliminarId(Integer id);
}
