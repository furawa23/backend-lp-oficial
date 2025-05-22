package com.alexander.sistema_cerro_verde_backend.service.recepcion;

import java.util.List;
import java.util.Optional;

import com.alexander.sistema_cerro_verde_backend.entity.recepcion.SalonesXReserva;

public interface SalonesReservaService {

    List<SalonesXReserva> buscarTodos();
    
    SalonesXReserva guardar(SalonesXReserva reservasalon);

    Optional<SalonesXReserva> buscarId(Integer id);

    SalonesXReserva modificar(SalonesXReserva habreserva);

    void deleteByReservaId(Integer idReserva);

    void eliminar(Integer id);
}
