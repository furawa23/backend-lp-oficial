package com.nube.sistema_hoteles.service.administrable;

import java.util.List;
import java.util.Optional;

import com.nube.sistema_hoteles.entity.Sucursales;

public interface SucursalesService {

    List<Sucursales> buscarTodos();
    
    Sucursales guardar(Sucursales sucursal);

    Optional<Sucursales> buscarId(Integer id);

    void modificar(Sucursales sucursal);

    void eliminar(Integer id);
}
