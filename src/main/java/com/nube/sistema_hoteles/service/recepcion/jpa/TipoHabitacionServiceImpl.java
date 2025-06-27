package com.nube.sistema_hoteles.service.recepcion.jpa;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.nube.sistema_hoteles.entity.Sucursales;
import com.nube.sistema_hoteles.entity.recepcion.TipoHabitacion;
import com.nube.sistema_hoteles.repository.recepcion.HabitacionesRepository;
import com.nube.sistema_hoteles.repository.recepcion.TipoHabitacionRepository;
import com.nube.sistema_hoteles.service.administrable.SucursalesService;
import com.nube.sistema_hoteles.service.recepcion.TipoHabitacionService;

import jakarta.persistence.EntityNotFoundException;

@Service
public class TipoHabitacionServiceImpl implements TipoHabitacionService {

     @Autowired
    private TipoHabitacionRepository repository;

    @Autowired
    private HabitacionesRepository habitacionesRepository;

    @Autowired
    private SucursalesService sucursalService;

    @Override
    @Transactional(readOnly = true)
    public List<TipoHabitacion> buscarTodos() {
    List<TipoHabitacion> resultados = repository.findAll();
    resultados.forEach(th -> {
        System.out.println("Datos recuperados: " + 
            th.getId_tipo_habitacion() + ", " + 
            th.getNombre() + ", " + 
            th.getPrecio());
    });
    return resultados;
    }

    @Override
    @Transactional
    public TipoHabitacion guardar(TipoHabitacion tipo) {
        if (tipo.getSucursal() != null && tipo.getSucursal().getId() != null) {
        Sucursales sucursal = sucursalService.buscarId(tipo.getSucursal().getId()).orElse(null);
        tipo.setSucursal(sucursal);
    }

        return repository.save(tipo);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<TipoHabitacion> buscarId(Integer id) {
        return repository.findById(id);
    }

    @Override
    public TipoHabitacion modificar(TipoHabitacion tipo) {
    if (tipo == null) {
        throw new IllegalArgumentException("La habitación no puede ser nula");
    }

    if (tipo.getId_tipo_habitacion() == null) {
        throw new IllegalArgumentException("El ID de la habitación es requerido");
    }

    return repository.findById(tipo.getId_tipo_habitacion())
        .map(existente -> {
            existente.setNombre(tipo.getNombre());
            existente.setPrecio(tipo.getPrecio());
            existente.setCantidadtipo(tipo.getCantidadtipo());
            return repository.save(existente);
        })
        .orElseThrow(() -> new EntityNotFoundException(
            "Tipo de habitacion no encontrada con ID: " + tipo.getId_tipo_habitacion()
        ));
    }


    @Override
    public void eliminar(Integer id) {
        TipoHabitacion tipo = repository.findById(id)
            .orElseThrow(() -> new RuntimeException("Tipo no encontrado"));

            Integer activas = habitacionesRepository.contarPorTipoHabitacion(id);
            if (activas != null && activas > 0) {
                throw new IllegalStateException("No se puede eliminar: hay habitaciones activas de este tipo.");
            }
            
        tipo.setEstado(0); 
        repository.save(tipo);
    }
}
