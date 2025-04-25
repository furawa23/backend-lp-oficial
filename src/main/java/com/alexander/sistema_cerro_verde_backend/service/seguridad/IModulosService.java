package com.alexander.sistema_cerro_verde_backend.service.seguridad;

import java.util.List;

import com.alexander.sistema_cerro_verde_backend.entity.seguridad.Modulos;

public interface IModulosService {
    Modulos crearModulo(Modulos modulos);
    Modulos obtenerModuloId(Integer idModulo);
    List<Modulos> obtenerTodosLooModulos();
    Modulos editarModulo(Modulos modulo);
    void eliminarModulo(Integer idModulo);   
}
