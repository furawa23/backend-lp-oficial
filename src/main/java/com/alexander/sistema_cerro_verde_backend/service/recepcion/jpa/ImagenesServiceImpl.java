package com.alexander.sistema_cerro_verde_backend.service.recepcion.jpa;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.alexander.sistema_cerro_verde_backend.entity.recepcion.Imagenes;
import com.alexander.sistema_cerro_verde_backend.repository.recepcion.ImagenesRepository;
import com.alexander.sistema_cerro_verde_backend.service.recepcion.ImagenesService;

@Service
public class ImagenesServiceImpl implements ImagenesService {

    @Autowired
    private ImagenesRepository repository;

    @Override
    @Transactional(readOnly = true)
    public List<Imagenes> buscarTodos() {
        return repository.findAll();
    }

    @Override
    @Transactional
    public Imagenes guardar(Imagenes imagen) {
        return repository.save(imagen);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Imagenes> buscarId(Integer id) {
        return repository.findById(id);
    }

    @Override
    @Transactional
    public void eliminar(Integer id) {
        repository.deleteById(id);
    }

    @Override
    public void modificar(Imagenes imagen) {
        repository.save(imagen);
    }
}
