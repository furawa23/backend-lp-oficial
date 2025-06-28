package com.nube.sistema_hoteles.repository.caja;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.nube.sistema_hoteles.entity.caja.ArqueosCaja;
import com.nube.sistema_hoteles.entity.caja.Cajas;

public interface ArqueosCajaRepository extends JpaRepository<ArqueosCaja, Integer> {
    Optional<ArqueosCaja> findByCaja(Cajas caja);
    List<ArqueosCaja> findAllByCaja(Cajas caja);
}
