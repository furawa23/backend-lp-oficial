package com.nube.sistema_hoteles.repository.seguridad;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.nube.sistema_hoteles.entity.seguridad.RolesPermisos;

import jakarta.transaction.Transactional;

@Repository
public interface RolesPermisosRepository extends  JpaRepository<RolesPermisos, Integer> {
    @Modifying
    @Transactional
    @Query("DELETE FROM RolesPermisos rp WHERE rp.roles.id = :idRol")
    void deleteByRolId(@Param("idRol") Integer idRol);

    Optional<RolesPermisos> findByRolesIdAndPermisosId(Integer idRol, Integer permisoId);
}
