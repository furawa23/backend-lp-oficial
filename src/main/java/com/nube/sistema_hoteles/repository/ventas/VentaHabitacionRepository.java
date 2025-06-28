package com.nube.sistema_hoteles.repository.ventas;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.nube.sistema_hoteles.entity.ventas.VentaHabitacion;

public interface VentaHabitacionRepository extends JpaRepository<VentaHabitacion, Integer> {

    List<VentaHabitacion> findByVenta_IdVenta(Integer id);
}
