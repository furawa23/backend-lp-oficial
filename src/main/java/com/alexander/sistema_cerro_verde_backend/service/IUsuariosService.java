package com.alexander.sistema_cerro_verde_backend.service;
import java.util.List;
import java.util.Set;
import com.alexander.sistema_cerro_verde_backend.entity.UsuarioRoles;
import com.alexander.sistema_cerro_verde_backend.entity.Usuarios;

public interface IUsuariosService {
        public Usuarios guardarUsuario(Usuarios usuario, Set<UsuarioRoles> usuarioRoles) throws Exception;
        public Usuarios obtenerUsuario(String username);
        public void eliminarUsuario(Long id);
        public Usuarios actualizarUsuario(Long id, Usuarios usuarioActualizado) throws Exception;
        public List<Usuarios> obtenerTodosUsuarios();
        public boolean existeUsuario(String username);
        public void cambiarContraseña(Long id, String nuevaContraseña) throws Exception;
        public void asignarRolesAUsuario(Long id, Set<UsuarioRoles> nuevosRoles) throws Exception;
        public void eliminarRolDeUsuario(Long id, Set<UsuarioRoles> rolesAEliminar) throws Exception;
    }
    
