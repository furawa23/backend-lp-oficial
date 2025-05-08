package com.alexander.sistema_cerro_verde_backend.service.recepcion.jpa;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.alexander.sistema_cerro_verde_backend.entity.recepcion.Recojo;
import com.alexander.sistema_cerro_verde_backend.repository.recepcion.RecojoRepository;
import com.alexander.sistema_cerro_verde_backend.service.recepcion.RecojoService;

@Service
public class RecojoServiceImpl implements RecojoService{

    @Autowired
    private RecojoRepository repository;

    @Override
    @Transactional(readOnly = true)
    public List<Recojo> buscarTodos() {
        return repository.findAll();
    }

    @Override
    @Transactional
    public Recojo guardar(Recojo recojo) {
        return repository.save(recojo);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Recojo> buscarId(Integer id) {
        return repository.findById(id);
    }

    @Override
    @Transactional
    public void eliminarId(Integer id) {
        repository.deleteById(id);
    }
}
