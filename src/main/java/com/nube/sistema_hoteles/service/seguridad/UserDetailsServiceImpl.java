package com.nube.sistema_hoteles.service.seguridad;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.nube.sistema_hoteles.entity.seguridad.Usuarios;
import com.nube.sistema_hoteles.repository.seguridad.UsuariosRepository;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private UsuariosRepository usuariosRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Usuarios usuario = this.usuariosRepository.findByUsername(username);
        if (usuario == null) {
            throw new UsernameNotFoundException("Usuario no encontrado");
        }
        return (UserDetails) usuario;
    }

}
