package com.alexander.sistema_cerro_verde_backend.repository.recepcion;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.alexander.sistema_cerro_verde_backend.entity.reservas.Clientes;

public interface ClientesRepository extends JpaRepository<Clientes, Integer> {

    @Query("SELECT c FROM Clientes c WHERE c.estado = 1")
    List<Clientes> findActive();

    Optional<Clientes> findByDniRucIgnoreCase(String rucDni);
}
