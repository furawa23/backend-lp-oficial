package com.nube.sistema_hoteles.service.compras;

import java.util.List;
import java.util.Optional;

import com.nube.sistema_hoteles.entity.compras.Proveedores;

public interface IProveedoresService {

    List<Proveedores> buscarTodos(); //Listar proveedores

    void guardar(Proveedores proveedor); //Guardar proveedores

    void modificar(Proveedores proveedor); //Modificar proveedores

    Optional<Proveedores> buscarId(String ruc_proveedor); //Buscar proveedor por Ruc y estado = 1

    void eliminar(String ruc_proveedor); //Eliminar proveedor, pasa a estado = 0
}