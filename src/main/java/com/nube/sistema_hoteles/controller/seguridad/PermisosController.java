package com.nube.sistema_hoteles.controller.seguridad;

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

import com.nube.sistema_hoteles.entity.seguridad.Permisos;
import com.nube.sistema_hoteles.service.seguridad.IPermisosService;

@RestController
@RequestMapping("/hoteleria/")
@CrossOrigin("*")
public class PermisosController {

    @Autowired
    private IPermisosService permisosService;

    @GetMapping("/permisos/")
    public List<Permisos> obtenerTodosLosPermisos() {
        return permisosService.obtenerTodosLosPermisos();
    }

    @GetMapping("/permisos/{id}")
    public Permisos obtenerPermisoPorId(@PathVariable Integer id) {
        return permisosService.obtenerPermiso(id);
    }
    @PostMapping("/permisos/")
    public Permisos crearPermiso(@RequestBody Permisos permiso) {
        return permisosService.crearPermiso(permiso);
    }

    @PutMapping("/permisos/{id}")
    public ResponseEntity<Permisos> actualizarModulo(@PathVariable Integer id, @RequestBody Permisos permisos) {
        permisos.setId(id); // Asegúrate que el ID del path coincida con el del objeto
        Permisos actualizado = permisosService.editarPermiso(permisos);
        return ResponseEntity.ok(actualizado);
    }  
    

    @DeleteMapping("/permisos/{id}")
    public void eliminarPermiso(@PathVariable Integer id) {
        permisosService.eliminarPermiso(id);
    }

  

}
