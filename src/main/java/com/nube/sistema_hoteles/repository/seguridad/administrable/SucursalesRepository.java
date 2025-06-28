package com.nube.sistema_hoteles.repository.seguridad.administrable;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.nube.sistema_hoteles.entity.seguridad.Sucursales;

public interface SucursalesRepository extends JpaRepository<Sucursales, Integer> {

    @Query("SELECT COALESCE(COUNT(r), 0) FROM Sucursales r WHERE r.empresa.id = :id AND r.estado = 1")
    Integer contarSucursalesActivasPorEmpresa(@Param("id") Integer id);

}
