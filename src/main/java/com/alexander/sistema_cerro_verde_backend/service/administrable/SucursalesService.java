package com.alexander.sistema_cerro_verde_backend.service.administrable;

import java.util.List;
import java.util.Optional;

import com.alexander.sistema_cerro_verde_backend.entity.Sucursales;

public interface SucursalesService {

    List<Sucursales> buscarTodos();
    
    Sucursales guardar(Sucursales sucursal);

    Optional<Sucursales> buscarId(Integer id);

    void modificar(Sucursales sucursal);

    void eliminar(Integer id);
}
