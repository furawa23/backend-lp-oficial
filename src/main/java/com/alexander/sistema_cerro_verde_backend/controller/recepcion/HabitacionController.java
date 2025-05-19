package com.alexander.sistema_cerro_verde_backend.controller.recepcion;

import java.util.List;
import java.util.Optional;

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


import com.alexander.sistema_cerro_verde_backend.entity.recepcion.Habitaciones;
import com.alexander.sistema_cerro_verde_backend.entity.recepcion.HabitacionesXImagenes;
import com.alexander.sistema_cerro_verde_backend.service.recepcion.HabitacionesImagenesService;
import com.alexander.sistema_cerro_verde_backend.service.recepcion.HabitacionesService;

import jakarta.persistence.EntityNotFoundException;


@CrossOrigin("*") 
@RestController
@RequestMapping("/cerro-verde/recepcion")
public class HabitacionController {

    @Autowired
    private HabitacionesService habitacionesService;

    @Autowired
    private HabitacionesImagenesService habimgService;

    @GetMapping("/habitaciones")
    public List<Habitaciones> buscarTodos() {
        return habitacionesService.buscarTodos();
    }

    @PostMapping("/habitaciones")
    public Habitaciones guardar(@RequestBody Habitaciones habitacion) {   
        habitacionesService.guardar(habitacion);     
        return habitacion;
    }

    
    @PutMapping("/habitaciones/{id}")
    public ResponseEntity<?> modificar(
        @PathVariable Integer id,
        @RequestBody Habitaciones habitacion) {
    
    try {
        habitacion.setId_habitacion(id); // Asegura que use el ID de la URL
        Habitaciones actualizada = habitacionesService.modificar(habitacion);
        return ResponseEntity.ok(actualizada);
        
    } catch (IllegalArgumentException e) {
        return ResponseEntity.badRequest().body(e.getMessage());
    } catch (EntityNotFoundException e) {
        return ResponseEntity.notFound().build();
    } catch (Exception e) {
        return ResponseEntity.internalServerError().body("Error al actualizar");
    }
    }

    @GetMapping("/habitaciones/{id}")
    public Optional<Habitaciones> buscarId(@PathVariable("id") Integer id) {
        return habitacionesService.buscarId(id);
    }

    @DeleteMapping("/habitaciones/eliminar/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Integer id) {
        habitacionesService.eliminar(id);
        return ResponseEntity.noContent().build(); // 204 No Content
    }

    @GetMapping("/habitaciones/imagenes")
    public List<HabitacionesXImagenes> buscarImagen() {
        return habimgService.buscarTodos();
    }

    @PostMapping("/habitaciones/imagenes")
    public HabitacionesXImagenes guardarImagen(@RequestBody HabitacionesXImagenes habimg) {   
        habimgService.guardar(habimg);     
        return habimg;
    }

    
    @PutMapping("/habitaciones/imagenes")
    public HabitacionesXImagenes modificar(@RequestBody HabitacionesXImagenes habimg) {   
        habimgService.modificar(habimg);
        return habimg;
    }

    @GetMapping("/habitaciones/imagenes/{id}")
    public Optional<HabitacionesXImagenes> buscarIdImagen(@PathVariable("id") Integer id) {
        return habimgService.buscarId(id);
    }

    @DeleteMapping("/habitaciones/imagenes/{id}")
    public String eliminarImagen(@PathVariable Integer id){
        habimgService.eliminar(id);
        return "Imagen de habitacion eliminada";
    }

    
}
