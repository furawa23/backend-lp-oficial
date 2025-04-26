package com.alexander.sistema_cerro_verde_backend.repository.seguridad;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.alexander.sistema_cerro_verde_backend.entity.seguridad.Submodulo;

@Repository
public interface SubModuloRepository extends JpaRepository<Submodulo, Integer> {
    List<Submodulo> findByModulo_IdModulo(Integer id);
}
