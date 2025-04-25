package com.alexander.sistema_cerro_verde_backend.service.caja;

import java.util.List;
import java.util.Optional;

import com.alexander.sistema_cerro_verde_backend.entity.caja.Cajas;

public interface CajasService {
    
    List<Cajas> buscarTodos();

    List<Cajas> buscarCajasCerradas();

    Optional<Cajas> buscarId(Integer id);

    Optional<Cajas> buscarCajaAperturada();

    Cajas guardar(Cajas caja);

    void eliminarId(Integer id);
}
