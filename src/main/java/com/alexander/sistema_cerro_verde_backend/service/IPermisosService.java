package com.alexander.sistema_cerro_verde_backend.service;
import java.util.List;
import com.alexander.sistema_cerro_verde_backend.entity.Permisos;

public interface IPermisosService {
    List<Permisos> obtenerTodosLosPermisos();
    Permisos editarPermiso(Permisos permisos); 
    Permisos obtenerPermiso(Long permisosId );
    Permisos crearPermiso(Permisos permiso);
    void eliminarPermiso(Long idPermiso);
}
