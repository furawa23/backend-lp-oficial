
package com.alexander.sistema_cerro_verde_backend.controller.seguridad;

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

import com.alexander.sistema_cerro_verde_backend.entity.seguridad.Sucursales;
import com.alexander.sistema_cerro_verde_backend.service.seguridad.administrable.SucursalesService;

import jakarta.persistence.EntityNotFoundException;

@CrossOrigin("*") 
@RestController
@RequestMapping("/hoteleria")
public class SucursalesController {
    
    @Autowired
    private SucursalesService sucursalService;

    @GetMapping("/sucursales")
    public List<Sucursales> buscarTodos() {
        return sucursalService.buscarTodos();
    }

    @PostMapping("/sucursales")
    public Sucursales guardar(@RequestBody Sucursales sucursal) {   
        sucursalService.guardar(sucursal);     
        return sucursal;
    }
    
    @PutMapping("/sucursales/{id}")
    public ResponseEntity<?> modificar(
        @PathVariable Integer id,
        @RequestBody Sucursales sucursal) {
    
    try {
        sucursal.setId(id); // Asegura que use el ID de la URL
        Sucursales actualizada = sucursalService.modificar(sucursal);
        return ResponseEntity.ok(actualizada);
        
    } catch (IllegalArgumentException e) {
        return ResponseEntity.badRequest().body(e.getMessage());
    } catch (EntityNotFoundException e) {
        return ResponseEntity.notFound().build();
    } catch (Exception e) {
        return ResponseEntity.internalServerError().body("Error al actualizar");
    }
    }

    @GetMapping("/sucursales/{id}")
    public Optional<Sucursales> buscarId(@PathVariable("id") Integer id) {
        return sucursalService.buscarId(id);
    }

    @DeleteMapping("/sucursales/{id}")
    public String eliminar(@PathVariable Integer id){
        sucursalService.eliminar(id);
        return "Tipo de habitacion eliminada";
    }
}






