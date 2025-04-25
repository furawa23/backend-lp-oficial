package com.alexander.sistema_cerro_verde_backend.service.seguridad.jpa;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alexander.sistema_cerro_verde_backend.entity.seguridad.Permisos;
import com.alexander.sistema_cerro_verde_backend.entity.seguridad.Submodulo;
import com.alexander.sistema_cerro_verde_backend.repository.seguridad.PermisosRepository;
import com.alexander.sistema_cerro_verde_backend.repository.seguridad.SubModuloRepository;
import com.alexander.sistema_cerro_verde_backend.service.seguridad.ISubModuloService;

@Service
public class SubmoduloService implements ISubModuloService {

    @Autowired
    private SubModuloRepository submoduloRepository;

    @Autowired
private PermisosRepository permisosRepository;
    @Override
    public List<Submodulo> obtenerTodosLosSubModulos() {
        return submoduloRepository.findAll();
    }

    @Override
    public List<Submodulo> obtenerSubModulosPorModuloId(Integer id) {
        return submoduloRepository.findByModulo_IdModulo(id);
    }
    @Override
    public Submodulo guardarSubModulo(Submodulo subModulos) {
        // 1. Guardar el submódulo primero
        Submodulo subModuloGuardado = submoduloRepository.save(subModulos);
    
        // 2. Crear permiso usando el submódulo ya persistido
        Permisos p = new Permisos();
        p.setSubModulo(subModuloGuardado);
        p.setEstado(true);
        p.setNombrePermiso(subModuloGuardado.getNombre());
    
        // 3. Guardar el permiso
        permisosRepository.save(p);
    
        return subModuloGuardado;
    }

}
