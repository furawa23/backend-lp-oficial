package com.alexander.sistema_cerro_verde_backend.service.recepcion.jpa;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.alexander.sistema_cerro_verde_backend.entity.recepcion.SalonesXReserva;
import com.alexander.sistema_cerro_verde_backend.repository.recepcion.SalonesReservaRepository;
import com.alexander.sistema_cerro_verde_backend.service.recepcion.SalonesReservaService;

import jakarta.persistence.EntityNotFoundException;

@Service
public class SalonesReservaServiceImpl implements SalonesReservaService {

    @Autowired
    private SalonesReservaRepository repository;

    @Override
    @Transactional(readOnly = true)
    public List<SalonesXReserva> buscarTodos() {
        return repository.findAll();
    }

    @Override
    @Transactional
    public SalonesXReserva guardar(SalonesXReserva salreserva) {
        return repository.save(salreserva);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<SalonesXReserva> buscarId(Integer id) {
        return repository.findById(id);
    }

    @Override
    public SalonesXReserva modificar(SalonesXReserva salreserva) {
    if (salreserva == null) {
        throw new IllegalArgumentException("el salón no puede ser nula");
    }

    // 2. Verificar que el ID existe
    if (salreserva.getId_salon_reserv() == null) {
        throw new IllegalArgumentException("El ID de el salón es requerido");
    }

    // 3. Comprobar si existe antes de actualizar
    return repository.findById(salreserva.getId_salon_reserv())
        .map(existente -> {
            existente.setSalon(salreserva.getSalon());
            existente.setReserva(salreserva.getReserva());
            return repository.save(existente);
        })
        .orElseThrow(() -> new EntityNotFoundException(
            "Habitación no encontrada con ID: " + salreserva.getId_salon_reserv()
        ));
    }

    @Override
    @Transactional
    public void eliminar(Integer id) {
        SalonesXReserva salreserva = repository.findById(id)
        .orElseThrow(() -> new RuntimeException("Habitación no encontrada"));
    
        salreserva.setEstado(0); // 0 representa inactivo/eliminado lógico
        repository.save(salreserva);
    }
}

