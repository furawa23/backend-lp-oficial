package com.alexander.sistema_cerro_verde_backend.service.jpa;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.alexander.sistema_cerro_verde_backend.entity.Permisos;
import com.alexander.sistema_cerro_verde_backend.repository.PermisosRepository;
import com.alexander.sistema_cerro_verde_backend.service.IPermisosService;

@Service
public class PermisosService implements IPermisosService{

    @Autowired
    private PermisosRepository permisosRepository;
    
  
    @Override
    public List<Permisos> obtenerTodosLosPermisos() {
        return permisosRepository.findAll();
    }

    @Override
    public Permisos editarPermiso(Permisos permisos) {
        return permisosRepository.save(permisos);
    }

    @Override
    public Permisos obtenerPermiso(Long permisosId) {
        return permisosRepository.findById(permisosId).get();
    }

    @Override
    public Permisos crearPermiso(Permisos permiso) {
        return permisosRepository.save(permiso);
    }

    @Override
    public void eliminarPermiso(Long idPermiso) {
        Permisos permisos = new Permisos();
        permisos.setIdPermisos(idPermiso);
        permisosRepository.delete(permisos);
    }
}