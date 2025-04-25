package com.alexander.sistema_cerro_verde_backend.service.seguridad.jpa;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alexander.sistema_cerro_verde_backend.entity.seguridad.Permisos;
import com.alexander.sistema_cerro_verde_backend.entity.seguridad.Roles;
import com.alexander.sistema_cerro_verde_backend.entity.seguridad.RolesPermisos;
import com.alexander.sistema_cerro_verde_backend.entity.seguridad.Usuarios;
import com.alexander.sistema_cerro_verde_backend.excepciones.CorreoYaRegistradoException;
import com.alexander.sistema_cerro_verde_backend.excepciones.UsuarioYaRegistradoException;
import com.alexander.sistema_cerro_verde_backend.repository.seguridad.PermisosRepository;
import com.alexander.sistema_cerro_verde_backend.repository.seguridad.RolesPermisosRepository;
import com.alexander.sistema_cerro_verde_backend.repository.seguridad.RolesRepository;
import com.alexander.sistema_cerro_verde_backend.repository.seguridad.UsuariosRepository;
import com.alexander.sistema_cerro_verde_backend.service.seguridad.IUsuariosService;

@Service
public class UsuariosService implements IUsuariosService {

    @Autowired
    private UsuariosRepository usuariosRepository;

    @Autowired
    private RolesRepository rolesRepository; 

    @Autowired
    private RolesPermisosRepository rolesPermisosRepository; 
    
    @Autowired
    private PermisosRepository permisosRepository; 

 
    
    @Override
    public Usuarios obtenerUsuario(String username) {
        return usuariosRepository.findByUsername(username);
    }
   
    @Override
    public Usuarios obtenerUsuarioPorId(Integer id) throws Exception {
        return usuariosRepository.findById(id).orElse(null);
    }




    @Override
    public void eliminarUsuario(Integer id) {
        usuariosRepository.deleteById(id);
    }

    @Override
    public Usuarios actualizarUsuario(Usuarios usuarioActualizado) throws Exception {
        return usuariosRepository.save(usuarioActualizado);
    }

    @Override
    public List<Usuarios> obtenerTodosUsuarios() {
        return usuariosRepository.findAll();
    }

    @Override
    public boolean existeUsuario(String username) {
        return usuariosRepository.findByUsername(username) != null;
    }

    @Override
    public void cambiarContraseña(Integer id, String nuevaContraseña) throws Exception {
        Optional<Usuarios> optional = usuariosRepository.findById(id);
        if (optional.isPresent()) {
            Usuarios usuario = optional.get();
            usuario.setPassword(nuevaContraseña);
            usuariosRepository.save(usuario);
        } else {
            throw new Exception("Usuario no encontrado para cambiar contraseña");
        }
    }


    @Override
    public Usuarios guardarUsuarioConPermisos(Usuarios usuario) throws Exception {
        Integer idRol = usuario.getRol().getId();
        Roles rol = rolesRepository.findById(idRol).orElse(null);
        
        if (rol == null) {
            throw new RuntimeException("Rol no encontrado");
        }

        usuario.setRol(rol);
        Usuarios usuarioGuardado = usuariosRepository.save(usuario);
        if (usuario.getRol().getRolesPermisos() != null) {
            for (RolesPermisos permiso : usuario.getRol().getRolesPermisos()) {
                // Asignamos el rol al permiso para asegurarnos
                permiso.setRoles(rol);
                rolesPermisosRepository.save(permiso);
            }
        }
        return usuarioGuardado;
    }

    @Override
    public Usuarios guardarUsuario(Usuarios usuario) throws Exception {
        if (usuariosRepository.existsByEmail(usuario.getEmail())) {
            throw new CorreoYaRegistradoException();
        }
    
        if (usuariosRepository.existsByUsername(usuario.getUsername())) {
            throw new UsuarioYaRegistradoException();  // 🚨 Aquí validas el username
        }
    
        Set<RolesPermisos> permisosDelJson = usuario.getRol().getRolesPermisos();
        Integer idRol = usuario.getRol().getId();
        Roles rol = rolesRepository.findById(idRol).orElse(null); 
        if (rol == null) {
            throw new RuntimeException("Rol no encontrado");
        }
    
        usuario.setRol(rol);
        Usuarios usuarioGuardado = usuariosRepository.save(usuario);
    
        if (permisosDelJson != null) {
            Set<RolesPermisos> nuevosPermisos = new HashSet<>();
    
            for (RolesPermisos rp : permisosDelJson) {
                Permisos permiso = permisosRepository.findById(rp.getPermisos().getId()).orElse(null);
                if (permiso != null) {
                    RolesPermisos nuevo = new RolesPermisos();
                    nuevo.setRoles(rol);
                    nuevo.setPermisos(permiso);
                    nuevosPermisos.add(nuevo);
                    rolesPermisosRepository.save(nuevo);
                }
            }
    
            rol.setRolesPermisos(nuevosPermisos);
        }
    
        return usuarioGuardado;
    }

    @Override
    public List<String> obtenerPermisosPorUsuarioId(Integer id) throws Exception {
        return usuariosRepository.obtenerPermisosPorUsuarioId(id);
    }
    
}    
