package com.nube.sistema_hoteles.repository.seguridad.administrable;

import org.springframework.data.jpa.repository.JpaRepository;

import com.nube.sistema_hoteles.entity.seguridad.Empresas;

public interface EmpresasRepository extends JpaRepository<Empresas, Integer> {

}
