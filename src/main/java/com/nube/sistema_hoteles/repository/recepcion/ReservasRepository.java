package com.nube.sistema_hoteles.repository.recepcion;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.nube.sistema_hoteles.entity.recepcion.Reservas;

public interface ReservasRepository extends JpaRepository<Reservas, Integer> {

    @Query("SELECT COUNT(r) > 0 FROM Reservas r WHERE r.cliente.idCliente = :clienteId")
    boolean existsReservas(@Param("clienteId") Integer clienteId);
}
