package com.alexander.sistema_cerro_verde_backend.service.jpa;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alexander.sistema_cerro_verde_backend.entity.Permisos;
import com.alexander.sistema_cerro_verde_backend.entity.Roles;
import com.alexander.sistema_cerro_verde_backend.entity.RolesPermisos;
import com.alexander.sistema_cerro_verde_backend.repository.PermisosRepository;
import com.alexander.sistema_cerro_verde_backend.repository.RolesPermisosRepository;
import com.alexander.sistema_cerro_verde_backend.repository.RolesRepository;
import com.alexander.sistema_cerro_verde_backend.service.IRolesService;

import jakarta.transaction.Transactional;

@Service
public class RolesService implements IRolesService {

    @Autowired
    private RolesRepository rolesRepository;
    @Autowired
    private PermisosRepository permisoRepository;
    

    @Autowired
    private RolesPermisosRepository rolesPermisosaRepo;
   
    @Autowired
    private RolesPermisosRepository rolesPermisosRepository;
    
    @Transactional
    public Roles crearRol(Roles rol) {
        // Guardar solo el rol
        
        System.out.println("Permisos recibidos:");
        for (RolesPermisos rp : rol.getRolesPermisos()) {
            if (rp.getPermisos() != null) {
                System.out.println(" -> ID Permiso: " + rp.getPermisos().getIdPermisos());
            } else {
                System.out.println(" -> Permiso nulo");
            }
        }
    
        return rolesRepository.save(rol);
    }
    


    @Override
    public Roles actualizarRol(Roles rol){
        return rolesRepository.save(rol);
    }

    @Override
    public Roles obtenerRolPorId(Integer idRol) {
        return rolesRepository.findById(idRol).get();
    }

    @Override
    public List<Roles> obtenerTodosLosRoles() {
        return rolesRepository.findAll();
    }


    @Override
    public void eliminarRol(Integer idRol) {
        if (!rolesRepository.existsById(idRol)) {
            throw new RuntimeException("El rol con ID " + idRol + " no existe");
        }
        rolesRepository.deleteById(idRol);
    }

    public Roles buscarPorNombre(String rol) {
        return rolesRepository.findByNombreRol(rol).orElse(null);
    }

    @Override
    public List<Permisos> obtenerPermisosPorRol(Integer idRol) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'obtenerPermisosPorRol'");
    }

    @Override
    public Roles asignarPermisosARol(Integer idRol, List<Integer> idPermisos) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'asignarPermisosARol'");
    }
}
