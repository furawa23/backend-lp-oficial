package com.nube.sistema_hoteles.repository.mantenimiento;

import org.springframework.data.jpa.repository.JpaRepository;

import com.nube.sistema_hoteles.entity.mantenimiento.Limpiezas;
import com.nube.sistema_hoteles.entity.mantenimiento.PersonalLimpieza;


public interface LimpiezasRepository extends JpaRepository <Limpiezas, Integer> {
    boolean existsByPersonal(PersonalLimpieza personal);
}