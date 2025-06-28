package com.nube.sistema_hoteles.service.seguridad;
import java.util.List;

import com.nube.sistema_hoteles.entity.seguridad.Permisos;
public interface IPermisosService {

    List<Permisos> obtenerTodosLosPermisos();
    Permisos editarPermiso(Permisos permisos); 
    Permisos obtenerPermiso(Integer permisosId);
    Permisos crearPermiso(Permisos permiso);
    void eliminarPermiso(Integer idPermiso);
    List<Permisos> obtenerPermisosPorModulo(Integer idModulo);
}
