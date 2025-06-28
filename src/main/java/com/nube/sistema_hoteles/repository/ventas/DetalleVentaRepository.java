package com.nube.sistema_hoteles.repository.ventas;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.nube.sistema_hoteles.entity.ventas.DetalleVenta;

public interface DetalleVentaRepository extends JpaRepository<DetalleVenta, Integer>{
    List<DetalleVenta> findByVenta_IdVenta(Integer id);
    void deleteAllByVenta_IdVenta(Integer id);
}
