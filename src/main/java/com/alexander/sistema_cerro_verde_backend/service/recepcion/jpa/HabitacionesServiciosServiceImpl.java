package com.alexander.sistema_cerro_verde_backend.service.recepcion.jpa;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.alexander.sistema_cerro_verde_backend.entity.recepcion.HabitacionesXServicios;
import com.alexander.sistema_cerro_verde_backend.repository.recepcion.HabitacionesServiciosRepository;
import com.alexander.sistema_cerro_verde_backend.service.recepcion.HabitacionesServiciosService;

@Service
public class HabitacionesServiciosServiceImpl implements HabitacionesServiciosService {

       @Autowired
    private HabitacionesServiciosRepository repository;

    @Override
    @Transactional(readOnly = true)
    public List<HabitacionesXServicios> buscarTodos() {
        return repository.findAll();
    }

    @Override
    @Transactional
    public HabitacionesXServicios guardar(HabitacionesXServicios habserv) {
        return repository.save(habserv);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<HabitacionesXServicios> buscarId(Integer id) {
        return repository.findById(id);
    }

    
    @Override
    @Transactional
    public void eliminarId(Integer id) {
        repository.deleteById(id);
    }
}
