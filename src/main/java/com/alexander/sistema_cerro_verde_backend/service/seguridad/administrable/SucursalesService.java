package com.alexander.sistema_cerro_verde_backend.service.seguridad.administrable;

import java.util.List;
import java.util.Optional;

import com.alexander.sistema_cerro_verde_backend.entity.seguridad.Sucursales;

public interface SucursalesService {

    List<Sucursales> buscarTodos();
    
    Sucursales guardar(Sucursales sucursal);

    Optional<Sucursales> buscarId(Integer id);

    Sucursales modificar(Sucursales sucursal);

    void eliminar(Integer id);
}
