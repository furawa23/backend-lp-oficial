package com.nube.sistema_hoteles.service.compras;

import java.util.List;
import java.util.Optional;

import com.nube.sistema_hoteles.entity.compras.Compras;

public interface IComprasService {
    List<Compras> buscarTodos();
    //Método para listar todos los registros 
    void guardar(Compras compra);

    void modificar(Compras compra);

    Optional<Compras> buscarId(Integer id_compra);

    void eliminar(Integer id_compra);

    String obtenerProximoCorrelativo();
}