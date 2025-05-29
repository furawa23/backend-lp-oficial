package com.alexander.sistema_cerro_verde_backend.repository.compras;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.alexander.sistema_cerro_verde_backend.entity.compras.Compras;

public interface ComprasRepository extends JpaRepository<Compras, Integer> {

    @Query("SELECT c FROM Compras c ORDER BY c.id_compra DESC")
    Optional<Compras> obtenerUltimaCompra();

}
