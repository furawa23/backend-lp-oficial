package com.alexander.sistema_cerro_verde_backend.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.alexander.sistema_cerro_verde_backend.entity.Permisos;
import com.alexander.sistema_cerro_verde_backend.entity.Roles;
import com.alexander.sistema_cerro_verde_backend.service.IRolesService;

@RestController
@RequestMapping("/roles")
@CrossOrigin(origins = "*") // Para permitir peticiones desde el frontend (ajusta según sea necesario)
public class RolesController {

    @Autowired
    private IRolesService rolesService;


    // Obtener todos los roles
    @GetMapping("/")
  public List<Roles> obtenerTodosLosPermisos() {
        return rolesService.obtenerTodosLosRoles();
    }

    @PutMapping("/")
    public ResponseEntity<Roles> actualizarPregunta(@RequestBody Roles rol){
        return ResponseEntity.ok(rolesService.actualizarRol(rol));
    }

    // Obtener un rol por su ID
    @GetMapping("/{id}")
    public ResponseEntity<Roles> obtenerRol(@PathVariable Long id) {
        Roles rol = rolesService.obtenerRolPorId(id);
        if (rol == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(rol);
    }

    // Crear un nuevo rol
    @PostMapping("/")
    public Roles crearRoles(@RequestBody Roles rol) {
        return rolesService.crearRol(rol);
    }

    // Actualizar un rol existnte

    @PutMapping("/{id}")
    public ResponseEntity<Roles> actualizarRol(@PathVariable Long id, @RequestBody Roles rol) {
        rol.setIdRol(id); // Asegúrate que el ID del path coincida con el del objeto
        Roles actualizado = rolesService.editarRol(rol);
        return ResponseEntity.ok(actualizado);
    }      


    // Eliminar un rol
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarRol(@PathVariable Long id) {
        Roles existente = rolesService.obtenerRolPorId(id);
        if (existente == null) {
            return ResponseEntity.notFound().build();
        }
        rolesService.eliminarRol(id);
        return ResponseEntity.noContent().build();
    }
}