package com.alexander.sistema_cerro_verde_backend.controller.recepcion;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.alexander.sistema_cerro_verde_backend.entity.recepcion.Imagenes;
import com.alexander.sistema_cerro_verde_backend.service.recepcion.ImagenesService;

@CrossOrigin("*") 
@RestController
@RequestMapping("/cerro-verde")
public class ImagenesController {

    @Autowired
    private ImagenesService imagenesService;

    @GetMapping("/imagenes")
    public List<Imagenes> buscarTodos() {
        return imagenesService.buscarTodos();
    }

    @PostMapping("/imagenes")
    public Imagenes guardar(@RequestBody Imagenes imagen) {   
        imagenesService.guardar(imagen);     
        return imagen;
    }

    
    @PutMapping("/imagenes")
    public Imagenes modificar(@RequestBody Imagenes imagen) {   
        imagenesService.modificar(imagen);
        return imagen;
    }

    @GetMapping("/imagenes/{id}")
    public Optional<Imagenes> buscarId(@PathVariable("id") Integer id) {
        return imagenesService.buscarId(id);
    }

    @DeleteMapping("/imagenes/{id}")
    public String eliminar(@PathVariable Integer id){
        imagenesService.eliminar(id);
        return "Imagen eliminada";
    }
}
