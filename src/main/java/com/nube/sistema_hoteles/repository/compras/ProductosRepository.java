package com.nube.sistema_hoteles.repository.compras;

import org.springframework.data.jpa.repository.JpaRepository;

import com.nube.sistema_hoteles.entity.compras.Productos;

public interface ProductosRepository extends JpaRepository<Productos, Integer> {
}