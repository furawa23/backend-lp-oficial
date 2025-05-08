package com.alexander.sistema_cerro_verde_backend.service.recepcion.jpa;

import com.alexander.sistema_cerro_verde_backend.service.recepcion.HabitacionesImagenesService;
import com.alexander.sistema_cerro_verde_backend.service.recepcion.HabitacionesService;
import com.alexander.sistema_cerro_verde_backend.service.recepcion.ImagenesService;
import com.alexander.sistema_cerro_verde_backend.repository.recepcion.HabitacionesImagenesRepository;
import com.alexander.sistema_cerro_verde_backend.entity.recepcion.Habitaciones;
import com.alexander.sistema_cerro_verde_backend.entity.recepcion.HabitacionesXImagenes;
import com.alexander.sistema_cerro_verde_backend.entity.recepcion.Imagenes;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class HabitacionesImagenesServiceImpl implements HabitacionesImagenesService {

    @Autowired
    private HabitacionesImagenesRepository repository;

    @Autowired
    private HabitacionesService habitacionService;

    @Autowired
    private ImagenesService imagenService;

    @Override
    @Transactional(readOnly = true)
    public List<HabitacionesXImagenes> buscarTodos() {
        return repository.findAll();
    }

    @Override
    @Transactional
    public HabitacionesXImagenes guardar(HabitacionesXImagenes habimg) {
    if (habimg.getHabitacion() != null && habimg.getHabitacion().getId_habitacion() != null) {
        Habitaciones habitacion = habitacionService.buscarId(habimg.getHabitacion().getId_habitacion()).orElse(null);
        habimg.setHabitacion(habitacion);
    }
               
    if (habimg.getImagen() != null && habimg.getImagen().getId_imagen() != null) {
        Imagenes imagen = imagenService.buscarId(habimg.getImagen().getId_imagen()).orElse(null);
        habimg.setImagen(imagen);
    }

    return repository.save(habimg);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<HabitacionesXImagenes> buscarId(Integer id) {
        return repository.findById(id);
    }

    @Override
    @Transactional
    public void eliminar(Integer id) {
        repository.deleteById(id);
    }

    @Override
    public void modificar(HabitacionesXImagenes habimg) {
        repository.save(habimg);
    }
}
