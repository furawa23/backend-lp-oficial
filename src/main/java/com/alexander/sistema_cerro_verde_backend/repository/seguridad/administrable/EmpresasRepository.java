package com.alexander.sistema_cerro_verde_backend.repository.seguridad.administrable;

import org.springframework.data.jpa.repository.JpaRepository;

import com.alexander.sistema_cerro_verde_backend.entity.seguridad.Empresas;

public interface EmpresasRepository extends JpaRepository<Empresas, Integer> {

}
