package com.alexander.sistema_cerro_verde_backend.repository;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.alexander.sistema_cerro_verde_backend.entity.Permisos;

@Repository
public interface PermisosRepository extends JpaRepository<Permisos,Long>{
    Optional<Permisos> findByNombrePermiso(String nombrePermiso);

    
} 