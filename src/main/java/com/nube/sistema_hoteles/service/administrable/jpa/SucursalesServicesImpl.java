package com.nube.sistema_hoteles.service.administrable.jpa;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.nube.sistema_hoteles.entity.Sucursales;
import com.nube.sistema_hoteles.repository.administrable.SucursalesRepository;
import com.nube.sistema_hoteles.service.administrable.SucursalesService;

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
    public void modificar(Sucursales sucursal) {
        repository.save(sucursal);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Sucursales> buscarId(Integer id) {
        return repository.findById(id);
    }

    @Override
    @Transactional
    public void eliminar(Integer id) {
        repository.deleteById(id);
    }

}
