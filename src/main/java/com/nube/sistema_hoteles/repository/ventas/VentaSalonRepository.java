package com.nube.sistema_hoteles.repository.ventas;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.nube.sistema_hoteles.entity.ventas.VentaSalon;

public interface VentaSalonRepository extends JpaRepository<VentaSalon, Integer> {

    List<VentaSalon> findByVenta_IdVenta(Integer id);

}
