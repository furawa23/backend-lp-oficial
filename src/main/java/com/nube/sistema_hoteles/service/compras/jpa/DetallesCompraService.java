package com.nube.sistema_hoteles.service.compras.jpa;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nube.sistema_hoteles.repository.compras.DetallesCompraRepository;
import com.nube.sistema_hoteles.service.compras.IDetallesCompraService;

@Service
public class DetallesCompraService implements IDetallesCompraService{
    @Autowired
    private DetallesCompraRepository repoDetallesCompra;
    
    @Override
    public void eliminarDetalleCompra (Integer idCompra) {
        repoDetallesCompra.eliminarPorIdCompra(idCompra);
    }
}