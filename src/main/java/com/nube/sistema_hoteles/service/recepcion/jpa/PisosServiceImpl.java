package com.nube.sistema_hoteles.service.recepcion.jpa;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import com.nube.sistema_hoteles.entity.Sucursales;
import com.nube.sistema_hoteles.entity.recepcion.Pisos;
import com.nube.sistema_hoteles.repository.recepcion.HabitacionesRepository;
import com.nube.sistema_hoteles.repository.recepcion.PisosRepository;
import com.nube.sistema_hoteles.service.administrable.SucursalesService;
import com.nube.sistema_hoteles.service.recepcion.PisosService;


@Service
public class PisosServiceImpl implements PisosService {

    @Autowired
    private PisosRepository repository;

    @Autowired
    private HabitacionesRepository habitacionesRepository;

    @Autowired
    private SucursalesService sucursalService;

    @Override
    @Transactional(readOnly = true)
    public List<Pisos> buscarTodos() {
        return repository.findAll();
    }

    @Override
    @Transactional
    public Pisos guardar(Pisos piso) {
        if (piso.getSucursal() != null && piso.getSucursal().getId() != null) {
        Sucursales sucursal = sucursalService.buscarId(piso.getSucursal().getId()).orElse(null);
        piso.setSucursal(sucursal);
    }

        return repository.save(piso);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Pisos> buscarId(Integer id) {
        return repository.findById(id);
    }
    
    @Override
    @Transactional
    public void eliminar(Integer id) {
        Pisos piso = repository.findById(id)
            .orElseThrow(() -> new RuntimeException("Piso no encontrado"));

        Integer habitacionesActivas = habitacionesRepository.contarHabitacionesActivasPorPiso(id);
        if (Optional.ofNullable(habitacionesActivas).orElse(0) > 0){
        throw new ResponseStatusException(
            HttpStatus.CONFLICT, "No se puede eliminar: el piso tiene recojos activos."
        );
        }

        piso.setEstado(0); 
        repository.save(piso);
    }
}

