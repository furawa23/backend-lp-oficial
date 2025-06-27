package com.nube.sistema_hoteles.repository.ventas;

import org.springframework.data.jpa.repository.JpaRepository;

import com.nube.sistema_hoteles.entity.ventas.Clientes;

public interface ClientesRepository extends JpaRepository<Clientes, Integer> {
}
