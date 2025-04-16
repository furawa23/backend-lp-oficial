package com.alexander.sistema_cerro_verde_backend.controller;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.alexander.sistema_cerro_verde_backend.entity.Roles;
import com.alexander.sistema_cerro_verde_backend.entity.UsuarioRoles;
import com.alexander.sistema_cerro_verde_backend.entity.Usuarios;
import com.alexander.sistema_cerro_verde_backend.service.IUsuariosService;
import com.alexander.sistema_cerro_verde_backend.service.jpa.RolesService;
import com.alexander.sistema_cerro_verde_backend.service.jpa.UsuariosService;

@RestController
@RequestMapping("/usuarios")
@CrossOrigin("*")
public class UsuarioController {

    @Autowired
    private IUsuariosService usuarioService;

    @Autowired
    private UsuariosService usuarioServiceImpl;
    @Autowired
    private RolesService rolesService;


    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @PostMapping("/")
    public Usuarios guardUsuario(@RequestBody Usuarios usuario, @RequestParam String rol) throws Exception {
        usuario.setPassword(this.bCryptPasswordEncoder.encode(usuario.getPassword()));
        Roles rolEncontrado = rolesService.buscarPorNombre(rol); // Este método busca el rol por nombre

        if (rolEncontrado == null) {
            throw new Exception("Rol no encontrado");
        }

        // Crear la relación entre usuario y rol
        Set<UsuarioRoles> roles = new HashSet<>();
        UsuarioRoles usuarioRol = new UsuarioRoles();
        usuarioRol.setRol(rolEncontrado);
        usuarioRol.setUsuario(usuario);
        roles.add(usuarioRol);

        // Guardar el usuario con el rol
        return usuarioService.guardarUsuario(usuario, roles);
    }



    @GetMapping("/{username}")
    public Usuarios obtenerUsuario(@PathVariable("username") String username) {
        return usuarioServiceImpl.obtenerUsuario(username);
    }

    @GetMapping("/")
    public List<Usuarios> listarUsuarios(){
        return usuarioServiceImpl.obtenerTodosUsuarios();
    }


    @DeleteMapping("/{usuarioId}")
    public void eliminarUsuario(@PathVariable("usuarioId") Long usuarioId){
        usuarioServiceImpl.eliminarUsuario(usuarioId);
    }
}
