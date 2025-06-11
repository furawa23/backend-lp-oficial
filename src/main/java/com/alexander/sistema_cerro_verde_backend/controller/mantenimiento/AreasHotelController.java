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

import com.alexander.sistema_cerro_verde_backend.entity.mantenimiento.AreasHotel;
import com.alexander.sistema_cerro_verde_backend.service.mantenimiento.jpa.AreasHotelService;



@RestController
@RequestMapping("/cerro-verde/areashotel")
public class AreasHotelController {
    @Autowired
    private AreasHotelService serviceAreasHotel;
    
    @GetMapping("/ver") //Ver
    public List<AreasHotel> buscarTodos() {
        return serviceAreasHotel.buscarTodos();
    }

    @GetMapping("/areashotel/{id}") //Ver por Id
    public Optional<AreasHotel> buscarPorId(@PathVariable Integer id){
        return serviceAreasHotel.buscarPorId(id);
    }

    @PostMapping("/registrar") //Registrar
    public AreasHotel registrar(@RequestBody AreasHotel areashotel) {
        serviceAreasHotel.registrar(areashotel);
        return areashotel;
    }

    @GetMapping("/actualizar/{id}") //Actualizar
    public void actualizar (@PathVariable Integer id, @RequestBody AreasHotel areashotel){
        serviceAreasHotel.actualizar(id, areashotel);
    }

    @GetMapping("/eliminar/{id}") //Eliminar
    public void eliminarPorId (@PathVariable Integer id){
        serviceAreasHotel.eliminarPorId(id);
    }

}