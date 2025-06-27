package com.nube.sistema_hoteles.service.caja;

import java.util.List;
import java.util.Optional;

import com.nube.sistema_hoteles.entity.caja.DetalleArqueo;

public interface DetalleArqueoService {

    List<DetalleArqueo> buscarTodos();

    Optional<DetalleArqueo> buscarId(Integer id);

    DetalleArqueo guardar(DetalleArqueo arqueo);

    void eliminarId(Integer id);

}
