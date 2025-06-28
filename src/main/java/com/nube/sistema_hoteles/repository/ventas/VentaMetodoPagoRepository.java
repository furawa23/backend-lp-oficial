package com.nube.sistema_hoteles.repository.ventas;

import org.springframework.data.jpa.repository.JpaRepository;

import com.nube.sistema_hoteles.entity.ventas.VentaMetodoPago;

public interface VentaMetodoPagoRepository extends JpaRepository<VentaMetodoPago, Integer>{
    void deleteAllByVenta_IdVenta(Integer id);
}
