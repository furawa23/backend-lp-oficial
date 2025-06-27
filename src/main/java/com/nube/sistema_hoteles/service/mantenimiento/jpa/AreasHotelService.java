package com.nube.sistema_hoteles.service.mantenimiento.jpa;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nube.sistema_hoteles.entity.mantenimiento.AreasHotel;
import com.nube.sistema_hoteles.repository.mantenimiento.AreasHotelRepository;
import com.nube.sistema_hoteles.service.mantenimiento.IAreasHotelService;

@Service
public class AreasHotelService implements IAreasHotelService{
    @Autowired
    private AreasHotelRepository repoAreasHotel;

    @Override //buscar todos
    public List<AreasHotel> buscarTodos() {
        return repoAreasHotel.findAll();
    }

    @Override //Buscar por id
    public Optional<AreasHotel> buscarPorId(Integer id) {
        return repoAreasHotel.findById(id);
    }

    @Override //Registrar
    public void registrar(AreasHotel areashotel) {
        repoAreasHotel.save(areashotel);
    }

    @Override //Actualizar
    public void actualizar (Integer id, AreasHotel areashotel) {
        AreasHotel metodoActualizar = repoAreasHotel.findById(id).orElse(null);
        metodoActualizar.setNombre(areashotel.getNombre());
        repoAreasHotel.save(metodoActualizar);
    }

    @Override //Eliminar
    public void eliminarPorId(Integer id) {
        repoAreasHotel.deleteById(id);
    }
}