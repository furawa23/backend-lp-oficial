package com.alexander.sistema_cerro_verde_backend.service.seguridad.administrable.jpa;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.alexander.sistema_cerro_verde_backend.entity.seguridad.Sucursales;
import com.alexander.sistema_cerro_verde_backend.repository.seguridad.administrable.SucursalesRepository;
import com.alexander.sistema_cerro_verde_backend.service.seguridad.administrable.SucursalesService;

import jakarta.persistence.EntityNotFoundException;

@Service
public class SucursalesServicesImpl implements SucursalesService {
    @Autowired
    private SucursalesRepository repository;

    @Override
    @Transactional(readOnly = true)
    public List<Sucursales> buscarTodos() {
        return repository.findAll();
    }

    @Override
    @Transactional
    public Sucursales guardar(Sucursales sucursal) {
        return repository.save(sucursal);
    }

    @Override
    public Sucursales modificar(Sucursales sucursal) {
        if (sucursal == null) {
            throw new IllegalArgumentException("La habitación no puede ser nula");
        }

        if (sucursal.getId() == null) {
            throw new IllegalArgumentException("El ID de la habitación es requerido");
        }

        return repository.findById(sucursal.getId())
            .map(existente -> {
                existente.setNombre(sucursal.getNombre());
                existente.setCiudad(sucursal.getCiudad());
                existente.setProvincia(sucursal.getProvincia());
                existente.setDepartamento(sucursal.getDepartamento());
                existente.setTelefono(sucursal.getTelefono());
                existente.setDireccion(sucursal.getDireccion());
                return repository.save(existente);
            })
            .orElseThrow(() -> new EntityNotFoundException(
                "Tipo de habitacion no encontrada con ID: " + sucursal.getId()
            ));
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Sucursales> buscarId(Integer id) {
        return repository.findById(id);
    }

    @Override
    @Transactional
    public void eliminar(Integer id) {
        Sucursales sucursal = repository.findById(id)
            .orElseThrow(() -> new RuntimeException("Tipo no encontrado"));
            
        sucursal.setEstado(0); 
        repository.save(sucursal);
    }

}
