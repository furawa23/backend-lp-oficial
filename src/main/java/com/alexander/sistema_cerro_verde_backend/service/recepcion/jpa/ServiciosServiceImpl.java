package com.alexander.sistema_cerro_verde_backend.service.recepcion.jpa;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.alexander.sistema_cerro_verde_backend.entity.recepcion.Servicios;
import com.alexander.sistema_cerro_verde_backend.repository.recepcion.ServiciosRepository;
import com.alexander.sistema_cerro_verde_backend.service.recepcion.ServiciosService;

@Service
public class ServiciosServiceImpl implements ServiciosService {

    @Autowired
    private ServiciosRepository repository;

    @Override
    @Transactional(readOnly = true)
    public List<Servicios> buscarTodos() {
        return repository.findAll();
    }

    @Override
    @Transactional
    public Servicios guardar(Servicios servicio) {
        return repository.save(servicio);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Servicios> buscarId(Integer id) {
        return repository.findById(id);
    }

    @Override
    @Transactional
    public void eliminarId(Integer id) {
        repository.deleteById(id);
    }

}
