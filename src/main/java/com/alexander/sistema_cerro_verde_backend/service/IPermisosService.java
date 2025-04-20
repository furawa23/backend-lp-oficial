package com.alexander.sistema_cerro_verde_backend.service;
import java.util.List;
import com.alexander.sistema_cerro_verde_backend.entity.Permisos;
public interface IPermisosService {

    List<Permisos> obtenerTodosLosPermisos();
    Permisos editarPermiso(Permisos permisos); 
    Permisos obtenerPermiso(Integer permisosId);
    Permisos crearPermiso(Permisos permiso);
    void eliminarPermiso(Integer idPermiso);
    List<Permisos> obtenerPermisosPorModulo(Integer idModulo);
}
