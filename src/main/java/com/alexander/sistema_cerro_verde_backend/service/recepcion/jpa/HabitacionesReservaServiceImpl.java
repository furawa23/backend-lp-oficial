package com.alexander.sistema_cerro_verde_backend.service.recepcion.jpa;

import com.alexander.sistema_cerro_verde_backend.service.recepcion.HabitacionesReservaService;
import jakarta.persistence.EntityNotFoundException;
import com.alexander.sistema_cerro_verde_backend.repository.recepcion.HabitacionesReservaRepository;
import com.alexander.sistema_cerro_verde_backend.entity.recepcion.HabitacionesXReserva;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class HabitacionesReservaServiceImpl implements HabitacionesReservaService {

    @Autowired
    private HabitacionesReservaRepository repository;

    @Override
    @Transactional(readOnly = true)
    public List<HabitacionesXReserva> buscarTodos() {
        return repository.findAll();
    }

    @Override
    @Transactional
    public HabitacionesXReserva guardar(HabitacionesXReserva habreserva) {
        return repository.save(habreserva);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<HabitacionesXReserva> buscarId(Integer id) {
        return repository.findById(id);
    }


    @Override
    public HabitacionesXReserva modificar(HabitacionesXReserva habreserva) {
    // 1. Validar que la habitación no sea nula
    if (habreserva == null) {
        throw new IllegalArgumentException("La habitación no puede ser nula");
    }

    // 2. Verificar que el ID existe
    if (habreserva.getId_hab_reserv() == null) {
        throw new IllegalArgumentException("El ID de la habitación es requerido");
    }

    // 3. Comprobar si existe antes de actualizar
    return repository.findById(habreserva.getId_hab_reserv())
        .map(existente -> {
            existente.setHabitacion(habreserva.getHabitacion());
            existente.setReserva(habreserva.getReserva());
            return repository.save(existente);
        })
        .orElseThrow(() -> new EntityNotFoundException(
            "Habitación no encontrada con ID: " + habreserva.getId_hab_reserv()
        ));
    }

    @Override
    @Transactional
    public void eliminar(Integer id) {
        HabitacionesXReserva habreserva = repository.findById(id)
        .orElseThrow(() -> new RuntimeException("Habitación no encontrada"));
    
        habreserva.setEstado(0); // 0 representa inactivo/eliminado lógico
        repository.save(habreserva);
    }
}
