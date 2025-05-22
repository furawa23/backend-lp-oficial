package com.alexander.sistema_cerro_verde_backend.repository.recepcion;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.alexander.sistema_cerro_verde_backend.entity.recepcion.SalonesXReserva;

import jakarta.transaction.Transactional;

public interface SalonesReservaRepository extends JpaRepository<SalonesXReserva, Integer> {

    @Modifying
    @Transactional
    @Query("DELETE FROM SalonesXReserva hr WHERE hr.reserva.id_reserva = :idReserva")
    void deleteByReservaId(@Param("idReserva") Integer idReserva);

    @Query("SELECT hxr FROM SalonesXReserva hxr WHERE hxr.salon.id = :id_salon AND hxr.reserva.id = :id_reserva")
    Optional<SalonesXReserva> findBySalonAndReserva(@Param("id_salon") Integer id_salon, @Param("id_reserva") Integer id_reserva);

}
