package com.alexander.sistema_cerro_verde_backend.service.jpa;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alexander.sistema_cerro_verde_backend.entity.Roles;
import com.alexander.sistema_cerro_verde_backend.entity.Usuarios;
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
    public Usuarios guardarUsuario(Usuarios usuario) throws Exception {
        Integer idRol = usuario.getRol().getIdRol();
        Roles rol = rolesRepository.findById(idRol).orElse(null);
        if(rol != null){
            usuario.setRol(rol);
            return usuariosRepository.save(usuario);
        }else{
            throw new RuntimeException("Modulo no encontrado");
        }
    }
        
        
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
}
