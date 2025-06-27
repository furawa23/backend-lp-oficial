package com.nube.sistema_hoteles.service.recepcion;

import java.util.List;
import java.util.Optional;

import com.nube.sistema_hoteles.entity.recepcion.SalonesXReserva;

public interface SalonesReservaService {

    List<SalonesXReserva> buscarTodos();
    
    SalonesXReserva guardar(SalonesXReserva reservasalon);

    Optional<SalonesXReserva> buscarId(Integer id);

    SalonesXReserva modificar(SalonesXReserva habreserva);

    void eliminar(Integer id);
}
