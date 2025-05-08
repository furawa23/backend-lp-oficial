package com.alexander.sistema_cerro_verde_backend.service.recepcion.jpa;
import com.alexander.sistema_cerro_verde_backend.repository.recepcion.SalonesImagenesRepository;
import com.alexander.sistema_cerro_verde_backend.entity.recepcion.SalonesXImagenes;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.alexander.sistema_cerro_verde_backend.service.recepcion.SalonesImagenesService;

@Service
public class SalonesImagenesServiceImpl implements SalonesImagenesService{

    @Autowired
    private SalonesImagenesRepository repository;

    @Override
    @Transactional(readOnly = true)
    public List<SalonesXImagenes> buscarTodos() {
        return repository.findAll();
    }

    @Override
    @Transactional
    public SalonesXImagenes guardar(SalonesXImagenes salimg) {
        return repository.save(salimg);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<SalonesXImagenes> buscarId(Integer id) {
        return repository.findById(id);
    }

    @Override
    @Transactional
    public void eliminarId(Integer id) {
        repository.deleteById(id);
    }
}
