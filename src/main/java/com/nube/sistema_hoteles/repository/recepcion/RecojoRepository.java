package com.nube.sistema_hoteles.repository.recepcion;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.nube.sistema_hoteles.entity.recepcion.Recojo;

public interface RecojoRepository extends JpaRepository<Recojo, Integer>{

    @Query("SELECT COALESCE(COUNT(r), 0) FROM Recojo r WHERE r.conductor.id_conductor = :idConductor AND r.estado = 1")
    Integer contarRecojosActivosPorConductor(@Param("idConductor") Integer idConductor);

}
