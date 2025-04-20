package com.alexander.sistema_cerro_verde_backend.controller;

import java.security.Principal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.alexander.sistema_cerro_verde_backend.config.JwtUtils;
import com.alexander.sistema_cerro_verde_backend.entity.JwtRequest;
import com.alexander.sistema_cerro_verde_backend.entity.JwtResponse;
import com.alexander.sistema_cerro_verde_backend.entity.Usuarios;
import com.alexander.sistema_cerro_verde_backend.excepciones.UsuarioFoundException;
import com.alexander.sistema_cerro_verde_backend.service.UserDetailsServiceImpl;

@RestController
@CrossOrigin("*")
@RequestMapping("/cerro-verde")
public class AuthenticationController {
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private UserDetailsServiceImpl userxDetailsServiceImpl; 


    @Autowired
    private JwtUtils jwtUtils; 
    
    @PostMapping("/generar-token")
    public ResponseEntity<?> generarToken(@RequestBody JwtRequest jwtRequest) throws Exception {
        System.out.println("vamos a generar el token con" + jwtRequest.getUsername());

        try {
            System.out.println("Intentando autenticar al usuario: " + jwtRequest.getUsername());
            autenticar(jwtRequest.getUsername(), jwtRequest.getPassword());
        } catch (UsuarioFoundException exception) {
            exception.printStackTrace();
            throw new Exception("Usuario no encontrado");
        }
    
        UserDetails userDetails = this.userxDetailsServiceImpl.loadUserByUsername(jwtRequest.getUsername());
        
        System.out.println("Usuario autenticado correctamente: " + userDetails.getUsername());
    
        String token = this.jwtUtils.generateToken(userDetails);
        
        System.out.println("Token generado: " + token); // <-- Esto te dirá si el token realmente se generó
    
        return ResponseEntity.ok(new JwtResponse(token));
    }
    

    private void autenticar(String username,String password) throws Exception {
        try{
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username,password));
        }catch (DisabledException exception){
            throw  new Exception("USUARIO DESHABILITADO " + exception.getMessage());
        }catch (BadCredentialsException e){
            throw  new Exception("Credenciales invalidas " + e.getMessage());
        }
    }


    @GetMapping("/usuario-actual")
    public Usuarios  obtenerUsuarioActual(Principal principal){
        System.out.println("Principal: " + principal);
        return (Usuarios) this.userxDetailsServiceImpl.loadUserByUsername(principal.getName());
    }
}
