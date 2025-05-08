package com.alexander.sistema_cerro_verde_backend.service.recepcion.jpa;

import com.alexander.sistema_cerro_verde_backend.service.recepcion.ConductoresService;
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
    @Transactional
    public void eliminarId(Integer id) {
        repository.deleteById(id);
    }
}
