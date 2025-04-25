package com.alexander.sistema_cerro_verde_backend.repository.caja;

import org.springframework.data.jpa.repository.JpaRepository;

import com.alexander.sistema_cerro_verde_backend.entity.caja.Cajas;
import com.alexander.sistema_cerro_verde_backend.entity.caja.TransaccionesCaja;
import java.util.List;

public interface TransaccionesCajaRepository extends JpaRepository<TransaccionesCaja, Integer> {
    List<TransaccionesCaja> findByCaja(Cajas caja);
}
