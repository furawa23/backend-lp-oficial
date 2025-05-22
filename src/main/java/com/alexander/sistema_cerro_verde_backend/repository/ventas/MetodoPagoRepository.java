package com.alexander.sistema_cerro_verde_backend.repository.ventas;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.alexander.sistema_cerro_verde_backend.entity.ventas.MetodosPago;

public interface MetodoPagoRepository extends JpaRepository<MetodosPago, Integer> {

    @Query("SELECT m FROM MetodosPago m WHERE m.estado = 1")
    List<MetodosPago> findActive();
}
