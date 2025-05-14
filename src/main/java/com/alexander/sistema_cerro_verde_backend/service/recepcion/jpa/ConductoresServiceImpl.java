package com.alexander.sistema_cerro_verde_backend.service.recepcion.jpa;

import com.alexander.sistema_cerro_verde_backend.service.recepcion.ConductoresService;

import jakarta.persistence.EntityNotFoundException;

import com.alexander.sistema_cerro_verde_backend.repository.recepcion.ConductoresRepository;
import com.alexander.sistema_cerro_verde_backend.entity.recepcion.Conductores;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ConductoresServiceImpl implements ConductoresService {

    @Autowired
    private ConductoresRepository repository;

    @Override
    @Transactional(readOnly = true)
    public List<Conductores> buscarTodos() {
        return repository.findAll();
    }

    @Override
    @Transactional
    public Conductores guardar(Conductores conductor) {
        return repository.save(conductor);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Conductores> buscarId(Integer id) {
        return repository.findById(id);
    }


    @Override
    public Conductores modificar(Conductores conductor) {
    if (conductor == null) {
        throw new IllegalArgumentException("El conductor no puede ser nulo");
    }

    if (conductor.getId_conductor() == null) {
        throw new IllegalArgumentException("El ID del conductor es requerido");
    }

    return repository.findById(conductor.getId_conductor())
        .map(existente -> {
            existente.setNombre(conductor.getNombre());
            existente.setPlaca(conductor.getPlaca());
            existente.setModelo_vehiculo(conductor.getModelo_vehiculo());
            return repository.save(existente);
        })
        .orElseThrow(() -> new EntityNotFoundException(
            "Conductor no encontrado con ID: " + conductor.getId_conductor()
        ));
    }

    
    @Override
    public void eliminar(Integer id) {
        Conductores conductor = repository.findById(id)
            .orElseThrow(() -> new RuntimeException("Conductor no encontrado"));

        conductor.setEstado(0); 
        repository.save(conductor);
    }
}
