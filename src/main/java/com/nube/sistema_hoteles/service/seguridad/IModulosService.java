package com.nube.sistema_hoteles.service.seguridad;

import java.util.List;

import com.nube.sistema_hoteles.entity.seguridad.Modulos;
import com.nube.sistema_hoteles.entity.seguridad.Permisos;

public interface IModulosService {
    Modulos crearModulo(Modulos modulos);
    Modulos obtenerModuloId(Integer idModulo);
    List<Modulos> obtenerTodosLooModulos();
    List<Permisos> obtenerPermisosPorModulo(Integer idModulo);

    Modulos editarModulo(Modulos modulo);
    void eliminarModulo(Integer idModulo);   
}
