package com.alexander.sistema_cerro_verde_backend.service.seguridad;

import java.util.List;

import com.alexander.sistema_cerro_verde_backend.entity.seguridad.UsuariosPermisos;

public interface IUsuariosPermisos {
    List<UsuariosPermisos> obtenerPermisosPorUsuario(Integer idUsuario);
    UsuariosPermisos asignarPermisoAUsuario(UsuariosPermisos usuariosPermisos);
    void eliminarPermisoDeUsuario(Integer idUsuariosPermisos);
    void eliminarTodosLosPermisosDeUsuario(Integer idUsuario);
}
