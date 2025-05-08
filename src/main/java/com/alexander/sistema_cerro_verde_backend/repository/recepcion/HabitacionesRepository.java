package com.alexander.sistema_cerro_verde_backend.repository.recepcion;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.alexander.sistema_cerro_verde_backend.entity.recepcion.Habitaciones;

public interface HabitacionesRepository extends JpaRepository<Habitaciones, Integer> {
@Query("SELECT COUNT(h) FROM Habitaciones h WHERE h.tipo_habitacion.id_tipo_habitacion = :idTipo")
    Integer contarPorTipoHabitacion(@Param("idTipo") Integer idTipoHabitacion);
}
