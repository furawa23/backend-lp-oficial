package com.nube.sistema_hoteles.service.caja;

import java.util.List;
import java.util.Optional;

import com.nube.sistema_hoteles.entity.caja.Cajas;
import com.nube.sistema_hoteles.entity.seguridad.Usuarios;

public interface CajasService {
    
    List<Cajas> buscarTodos();

    List<Cajas> buscarCajasCerradas();

    Optional<Cajas> buscarId(Integer id);

    Optional<Cajas> buscarCajaAperturada();

    Cajas guardar(Cajas caja);

    void eliminarId(Integer id);

    Optional<Cajas> buscarCajaAperturadaPorUsuario(Usuarios usuario);
    
    Optional<Cajas> buscarCajaPorUsuario(Usuarios usuario);

}
