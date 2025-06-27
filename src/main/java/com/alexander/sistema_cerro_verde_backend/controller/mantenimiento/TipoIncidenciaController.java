package com.alexander.sistema_cerro_verde_backend.controller.mantenimiento;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.alexander.sistema_cerro_verde_backend.entity.mantenimiento.TipoIncidencia;
import com.alexander.sistema_cerro_verde_backend.service.mantenimiento.jpa.TipoIncidenciaService;



@RestController
@RequestMapping("/hoteleria/tipoincidencia")
@CrossOrigin("*")
public class TipoIncidenciaController {
    @Autowired
    private TipoIncidenciaService serviceTipoIncidencia;
    
    @GetMapping("/ver") //Ver
    public List<TipoIncidencia> buscarTodos() {
        return serviceTipoIncidencia.buscarTodos();
    }

    @GetMapping("/tipoincidencia/{id}") //Ver por Id
    public Optional<TipoIncidencia> buscarPorId(@PathVariable Integer id){
        return serviceTipoIncidencia.buscarPorId(id);
    }

    @PostMapping("/registrar") //Registrar
    public TipoIncidencia registrar(@RequestBody TipoIncidencia tipoincidencia) {
        serviceTipoIncidencia.registrar(tipoincidencia);
        return tipoincidencia;
    }

    @GetMapping("/actualizar/{id}") //Actualizar
    public void actualizar (@PathVariable Integer id, @RequestBody TipoIncidencia tipoincidencia){
        serviceTipoIncidencia.actualizar(id, tipoincidencia);
    }

    @GetMapping("/eliminar/{id}") //Eliminar
    public void eliminarPorId (@PathVariable Integer id){
        serviceTipoIncidencia.eliminarPorId(id);
    }

}