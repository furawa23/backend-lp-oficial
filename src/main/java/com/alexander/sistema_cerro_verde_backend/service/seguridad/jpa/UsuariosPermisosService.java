package com.alexander.sistema_cerro_verde_backend.service.seguridad.jpa;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.alexander.sistema_cerro_verde_backend.entity.seguridad.UsuariosPermisos;
import com.alexander.sistema_cerro_verde_backend.repository.seguridad.UsuariosPermisosRepository;
import com.alexander.sistema_cerro_verde_backend.service.seguridad.IUsuariosPermisos;

public class UsuariosPermisosService  implements IUsuariosPermisos {

    @Autowired
    private UsuariosPermisosRepository usuariosPermisosRepository;

    @Override
    public List<UsuariosPermisos> obtenerPermisosPorUsuario(Integer idUsuario) {

        return usuariosPermisosRepository.findByUsuarios_IdUsuario(idUsuario);
    }

    @Override
    public UsuariosPermisos asignarPermisoAUsuario(UsuariosPermisos usuariosPermisos) {
        return usuariosPermisosRepository.save(usuariosPermisos);
    }

    @Override
    public void eliminarPermisoDeUsuario(Integer idUsuariosPermisos) {
        usuariosPermisosRepository.deleteById(idUsuariosPermisos);
    }

    @Override
    public void eliminarTodosLosPermisosDeUsuario(Integer idUsuario) {
        usuariosPermisosRepository.deleteByUsuarios_IdUsuario(idUsuario);
    }

}
