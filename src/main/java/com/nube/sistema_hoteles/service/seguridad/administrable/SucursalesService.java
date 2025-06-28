
package com.nube.sistema_hoteles.service.seguridad.administrable;

import java.util.List;
import java.util.Optional;


import com.nube.sistema_hoteles.entity.seguridad.Sucursales;

public interface SucursalesService {

    List<Sucursales> buscarTodos();
    
    Sucursales guardar(Sucursales sucursal);

    Optional<Sucursales> buscarId(Integer id);

    Sucursales modificar(Sucursales sucursal);

    void eliminar(Integer id);
}
