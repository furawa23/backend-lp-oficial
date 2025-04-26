package com.alexander.sistema_cerro_verde_backend.repository.seguridad;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.alexander.sistema_cerro_verde_backend.entity.seguridad.UsuariosPermisos;

public interface  UsuariosPermisosRepository extends  JpaRepository<UsuariosPermisos, Integer>{
    List<UsuariosPermisos> findByUsuarios_IdUsuario(Integer idUsuario);
    void deleteByUsuarios_IdUsuario(Integer idUsuario);
}
