package com.alexander.sistema_cerro_verde_backend.controller.mantenimiento;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.alexander.sistema_cerro_verde_backend.entity.mantenimiento.Incidencias;
import com.alexander.sistema_cerro_verde_backend.service.mantenimiento.jpa.IncidenciasService;



@RestController
@RequestMapping("/cerro-verde/incidencias")
public class IncidenciasController {
    @Autowired
    private IncidenciasService serviceIncidencias;
    
    @GetMapping("/ver") //Ver
    public List<Incidencias> buscarTodos() {
        return serviceIncidencias.buscarTodos();
    }

    @GetMapping("/incidencias/{id}") //Ver por Id
    public Optional<Incidencias> buscarPorId(@PathVariable Integer id){
        return serviceIncidencias.buscarPorId(id);
    }

    @PostMapping("/registrar") //Registrar
    public Incidencias registrar(@RequestBody Incidencias incidencias) {
        serviceIncidencias.registrar(incidencias);
        return incidencias;
    }

    @GetMapping("/actualizar/{id}") //Actualizar
    public void actualizar (@PathVariable Integer id, @RequestBody Incidencias incidencias){
        serviceIncidencias.actualizar(id, incidencias);
    }

    @GetMapping("/eliminar/{id}") //Eliminar
    public void eliminarPorId (@PathVariable Integer id){
        serviceIncidencias.eliminarPorId(id);
    }

}