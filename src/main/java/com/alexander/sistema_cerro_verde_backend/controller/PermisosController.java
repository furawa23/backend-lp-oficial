package com.alexander.sistema_cerro_verde_backend.controller;

import com.alexander.sistema_cerro_verde_backend.entity.Permisos;
import com.alexander.sistema_cerro_verde_backend.service.IPermisosService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/permisos")
@CrossOrigin("*")
public class PermisosController {

    @Autowired
    private IPermisosService permisosService;

    // Obtener todos los permisos
    @GetMapping("/")
    public List<Permisos> obtenerTodosLosPermisos() {
        return permisosService.obtenerTodosLosPermisos();
    }

    // Obtener un permiso por ID
    @GetMapping("/{id}")
    public Permisos obtenerPermisoPorId(@PathVariable Long id) {
        return permisosService.obtenerPermiso(id);
    }

    // Crear un nuevo permiso
    @PostMapping("/")
    public Permisos crearPermiso(@RequestBody Permisos permiso) {
        return permisosService.crearPermiso(permiso);
    }

    // Editar un permiso existente
    @PutMapping("/")
    public Permisos editarPermiso(@RequestBody Permisos permiso) {
        return permisosService.editarPermiso(permiso);
    }

    // Eliminar un permiso por ID
    @DeleteMapping("/{id}")
    public void eliminarPermiso(@PathVariable Long id) {
        permisosService.eliminarPermiso(id);
    }
}
