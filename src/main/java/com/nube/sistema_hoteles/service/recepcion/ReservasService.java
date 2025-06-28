package com.nube.sistema_hoteles.service.recepcion;

import java.util.List;
import java.util.Optional;

import com.nube.sistema_hoteles.entity.recepcion.Reservas;

public interface ReservasService {
    List<Reservas> buscarTodos();
    
    Reservas guardar(Reservas reserva);

    Optional<Reservas> buscarId(Integer id);

    Reservas modificar(Reservas reserva);

    void cancelar(Integer id);

    void eliminar(Integer id);

}
