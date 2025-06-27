package com.nube.sistema_hoteles.service.recepcion;

import java.util.List;
import java.util.Optional;

import com.nube.sistema_hoteles.entity.recepcion.CheckinCheckout;

public interface CheckinCheckoutService {

    List<CheckinCheckout> buscarTodos();
    
    CheckinCheckout guardar(CheckinCheckout check);

    Optional<CheckinCheckout> buscarId(Integer id);

    CheckinCheckout modificar(CheckinCheckout check);

    void eliminar(Integer id);
}
