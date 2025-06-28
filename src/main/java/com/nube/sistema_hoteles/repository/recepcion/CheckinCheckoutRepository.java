package com.nube.sistema_hoteles.repository.recepcion;

import org.springframework.data.jpa.repository.JpaRepository;

import com.nube.sistema_hoteles.entity.recepcion.CheckinCheckout;

public interface CheckinCheckoutRepository extends JpaRepository<CheckinCheckout, Integer>{

    
}
