package com.nube.sistema_hoteles.service.caja;

import java.util.List;
import java.util.Optional;

import com.nube.sistema_hoteles.entity.caja.Cajas;
import com.nube.sistema_hoteles.entity.caja.TipoTransacciones;
import com.nube.sistema_hoteles.entity.caja.TransaccionesCaja;

public interface TransaccionesCajaService {
    
    List<TransaccionesCaja> buscarTodos();

    Optional<TransaccionesCaja> encontrarId(Integer id);

    TransaccionesCaja guardar(TransaccionesCaja transaccion);

    void eliminarId(Integer id);
    
    List<TransaccionesCaja> buscarPorCaja(Cajas caja);

    TipoTransacciones obtenerTipoPorId(Integer id);
    
}
