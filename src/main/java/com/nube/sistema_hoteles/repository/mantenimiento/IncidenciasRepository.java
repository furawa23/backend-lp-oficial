package com.nube.sistema_hoteles.repository.mantenimiento;

import org.springframework.data.jpa.repository.JpaRepository;

import com.nube.sistema_hoteles.entity.mantenimiento.AreasHotel;
import com.nube.sistema_hoteles.entity.mantenimiento.Incidencias;
import com.nube.sistema_hoteles.entity.mantenimiento.TipoIncidencia;


public interface IncidenciasRepository extends JpaRepository <Incidencias, Integer>{
    boolean existsByArea(AreasHotel area);
    boolean existsByTipoIncidencia(TipoIncidencia tipo);
}