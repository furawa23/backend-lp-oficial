package com.nube.sistema_hoteles.repository.seguridad;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.nube.sistema_hoteles.entity.seguridad.Roles;

@Repository
public interface RolesRepository extends JpaRepository<Roles, Integer> {
    Optional<Roles> findByNombreRol(String nombreRol);
}
