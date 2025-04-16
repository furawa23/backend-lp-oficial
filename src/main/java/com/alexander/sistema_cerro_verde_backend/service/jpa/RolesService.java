package com.alexander.sistema_cerro_verde_backend.service.jpa;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.alexander.sistema_cerro_verde_backend.entity.Roles;
import com.alexander.sistema_cerro_verde_backend.repository.RolesRepository;
import com.alexander.sistema_cerro_verde_backend.service.IRolesService;

@Service
public class RolesService implements IRolesService {

    @Autowired
    private RolesRepository rolesRepository;

    @Override
    public Roles crearRol(Roles rol) {
        return rolesRepository.save(rol);
    }

    @Override
    public Roles actualizarRol(Roles rol){
        return rolesRepository.save(rol);
    }

    @Override
    public Roles obtenerRolPorId(Long idRol) {
        return rolesRepository.findById(idRol).get();
    }

    @Override
    public List<Roles> obtenerTodosLosRoles() {
        return rolesRepository.findAll();
    }

    @Override 
    public Roles editarRol(Roles rol) {
        Optional<Roles> existente = rolesRepository.findById(rol.getIdRol());
        if (existente.isPresent()) {
            return rolesRepository.save(rol);
        } else {
            throw new RuntimeException("El rol con ID " + rol.getIdRol() + " no existe.");
        }
    }

    @Override
    public void eliminarRol(Long idRol) {
        if (!rolesRepository.existsById(idRol)) {
            throw new RuntimeException("El rol con ID " + idRol + " no existe");
        }
        rolesRepository.deleteById(idRol);
    }

    public Roles buscarPorNombre(String rol) {
        return rolesRepository.findByNombreRol(rol).orElse(null);
    }
}
