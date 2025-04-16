package com.alexander.sistema_cerro_verde_backend.service.jpa;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.alexander.sistema_cerro_verde_backend.entity.UsuarioRoles;
import com.alexander.sistema_cerro_verde_backend.entity.Usuarios;
import com.alexander.sistema_cerro_verde_backend.excepciones.UsuarioFoundException;
import com.alexander.sistema_cerro_verde_backend.repository.RolesRepository;
import com.alexander.sistema_cerro_verde_backend.repository.UsuariosRepository;
import com.alexander.sistema_cerro_verde_backend.service.IUsuariosService;

@Service
public class UsuariosService implements IUsuariosService {

    @Autowired
    private UsuariosRepository usuariosRepository;

    @Autowired
    private RolesRepository rolesRepository; 

    @Override
    public Usuarios guardarUsuario(Usuarios usuario, Set<UsuarioRoles> usuarioRoles) throws Exception {
        Usuarios usuarioLocal = usuariosRepository.findByUsername(usuario.getUsername());
        
        if (usuarioLocal != null) {
            System.out.println("El usuario ingresado ya existe");
            throw new UsuarioFoundException("El usuario ya esta presente");
        } else {
            for (UsuarioRoles usuarioRol : usuarioRoles) {
                rolesRepository.save(usuarioRol.getRol());
            }
            usuario.getUsuarioRoles().addAll(usuarioRoles);
            usuarioLocal = usuariosRepository.save(usuario);
        }
        
        return usuarioLocal;
    }

    public Usuarios obtenerUsuario(String username) {
        return usuariosRepository.findByUsername(username);
    }

    public void eliminarUsuario(Long id) {
        usuariosRepository.deleteById(id);
    }

    @Override
    public Usuarios actualizarUsuario(Long id, Usuarios usuarioActualizado) throws Exception {
        Optional<Usuarios> optional = usuariosRepository.findById(id);
        
        if (optional.isPresent()) {
            Usuarios usuarioExistente = optional.get();
            return usuariosRepository.save(usuarioExistente);
        } else {
            throw new Exception("Usuario no encontrado para actualizar");
        }
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
    public void cambiarContraseña(Long id, String nuevaContraseña) throws Exception {
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
    public void asignarRolesAUsuario(Long id, Set<UsuarioRoles> nuevosRoles) throws Exception {
        Optional<Usuarios> optional = usuariosRepository.findById(id);
        
        if (optional.isPresent()) {
            Usuarios usuario = optional.get();
            for (UsuarioRoles rol : nuevosRoles) {
                rolesRepository.save(rol.getRol());
            }
            usuario.getUsuarioRoles().addAll(nuevosRoles);
            usuariosRepository.save(usuario);
        } else {
            throw new Exception("Usuario no encontrado para asignar roles");
        }
    }

    @Override
    public void eliminarRolDeUsuario(Long id, Set<UsuarioRoles> rolesAEliminar) throws Exception {
        Optional<Usuarios> optional = usuariosRepository.findById(id);
        
        if (optional.isPresent()) {
            Usuarios usuario = optional.get();
            usuario.getUsuarioRoles().removeIf(rolesAEliminar::contains);
            usuariosRepository.save(usuario);
        } else {
            throw new Exception("Usuario no encontrado para eliminar roles");
        }
    }
}
