package com.nube.sistema_hoteles.service.seguridad.jpa;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nube.sistema_hoteles.entity.seguridad.Modulos;
import com.nube.sistema_hoteles.entity.seguridad.Permisos;
import com.nube.sistema_hoteles.repository.seguridad.ModuloRepository;
import com.nube.sistema_hoteles.repository.seguridad.PermisosRepository;
import com.nube.sistema_hoteles.service.seguridad.IModulosService;

@Service
public class ModuloService implements IModulosService{

    @Autowired
    private ModuloRepository moduloRepository;

    @Autowired
    private PermisosRepository permisosRepository;

    @Override
    public Modulos crearModulo(Modulos modulo) {
        return moduloRepository.save(modulo);
    }
    
    @Override
    public Modulos obtenerModuloId(Integer idModulo) {
        return moduloRepository.findById(idModulo).orElse(null);
    }

    @Override
    public List<Modulos> obtenerTodosLooModulos() {
        return moduloRepository.findAll();
    }

    @Override
    public Modulos editarModulo(Modulos modulo) {
        return moduloRepository.save(modulo);
    }

    @Override
    public void eliminarModulo(Integer idModulo) {
        moduloRepository.deleteById(idModulo);
    }

    @Override
    public List<Permisos> obtenerPermisosPorModulo(Integer idModulo) {
        return permisosRepository.findByModulo_IdModulo(idModulo);
    }
    

}
