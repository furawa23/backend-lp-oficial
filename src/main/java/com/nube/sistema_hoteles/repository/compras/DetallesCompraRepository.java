package com.nube.sistema_hoteles.repository.compras;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.nube.sistema_hoteles.entity.compras.DetallesCompra;

import jakarta.transaction.Transactional;

public interface DetallesCompraRepository extends JpaRepository<DetallesCompra, Integer> {
    @Modifying
    @Transactional
    @Query("DELETE FROM DetallesCompra d WHERE d.compra.id_compra = :idCompra")
    void eliminarPorIdCompra(@Param("idCompra") Integer idCompra);
}