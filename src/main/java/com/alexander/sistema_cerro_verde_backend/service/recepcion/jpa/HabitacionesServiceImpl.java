package com.alexander.sistema_cerro_verde_backend.service.recepcion.jpa;

import com.alexander.sistema_cerro_verde_backend.service.administrable.SucursalesService;
import com.alexander.sistema_cerro_verde_backend.service.recepcion.HabitacionesService;
import com.alexander.sistema_cerro_verde_backend.service.recepcion.TipoHabitacionService;

import jakarta.persistence.EntityNotFoundException;

import com.alexander.sistema_cerro_verde_backend.repository.recepcion.HabitacionesRepository;
import com.alexander.sistema_cerro_verde_backend.repository.recepcion.HabitacionesReservaRepository;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import com.alexander.sistema_cerro_verde_backend.entity.Sucursales;
import com.alexander.sistema_cerro_verde_backend.entity.recepcion.Habitaciones;
import com.alexander.sistema_cerro_verde_backend.entity.recepcion.TipoHabitacion;

@Service
public class HabitacionesServiceImpl implements HabitacionesService {
   
@Autowired
private HabitacionesRepository repository;

@Autowired
private SucursalesService sucursalService;

@Autowired
private HabitacionesReservaRepository reservaRepository;

@Autowired
private TipoHabitacionService tipoHabitacionService;

    @Override
    @Transactional(readOnly = true)
    public List<Habitaciones> buscarTodos() {
        return repository.findAll();
    }
    
    @Override
    @Transactional
    public Habitaciones guardar(Habitaciones habitacion) {
    if (habitacion.getSucursal() != null && habitacion.getSucursal().getId() != null) {
        Sucursales sucursal = sucursalService.buscarId(habitacion.getSucursal().getId()).orElse(null);
        habitacion.setSucursal(sucursal);
    }
        
        

    if (habitacion.getTipo_habitacion() != null && habitacion.getTipo_habitacion().getId_tipo_habitacion() != null) {
        TipoHabitacion tipo = tipoHabitacionService.buscarId(habitacion.getTipo_habitacion().getId_tipo_habitacion()).orElse(null);
        habitacion.setTipo_habitacion(tipo);
    }

    return repository.save(habitacion);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Habitaciones> buscarId(Integer id) {
        return repository.findById(id);
    }

    @Override
    @Transactional
    public void eliminar(Integer id) {
        Habitaciones habitacion = repository.findById(id)
            .orElseThrow(() -> new RuntimeException("Habitación no encontrada"));

        Integer reservasActivas = reservaRepository.contarReservasActivasPorHabitacion(id);
        if (reservasActivas != null && reservasActivas > 0) {
        throw new ResponseStatusException(
            HttpStatus.CONFLICT, "No se puede eliminar: la habitación tiene reservas activas."
        );
        }
        
        habitacion.setEstado(0); // baja lógica
        repository.save(habitacion);
    }


    @Override
    public Habitaciones modificar(Habitaciones habitacion) {
    // 1. Validar que la habitación no sea nula
    if (habitacion == null) {
        throw new IllegalArgumentException("La habitación no puede ser nula");
    }

    // 2. Verificar que el ID existe
    if (habitacion.getId_habitacion() == null) {
        throw new IllegalArgumentException("El ID de la habitación es requerido");
    }

    // 3. Comprobar si existe antes de actualizar
    return repository.findById(habitacion.getId_habitacion())
        .map(existente -> {
            existente.setPiso(habitacion.getPiso());
            existente.setNumero(habitacion.getNumero());
            existente.setTipo_habitacion(habitacion.getTipo_habitacion());
            existente.setEstado_habitacion(habitacion.getEstado_habitacion());
            return repository.save(existente);
        })
        .orElseThrow(() -> new EntityNotFoundException(
            "Habitación no encontrada con ID: " + habitacion.getId_habitacion()
        ));
    }

    
}
