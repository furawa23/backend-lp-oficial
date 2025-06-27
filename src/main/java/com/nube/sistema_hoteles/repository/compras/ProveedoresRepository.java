package com.nube.sistema_hoteles.repository.compras;

import org.springframework.data.jpa.repository.JpaRepository;

import com.nube.sistema_hoteles.entity.compras.Proveedores;

public interface ProveedoresRepository extends JpaRepository<Proveedores, String> {
}
