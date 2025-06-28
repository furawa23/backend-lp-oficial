package com.nube.sistema_hoteles.service.seguridad;
import java.util.List;

import com.nube.sistema_hoteles.entity.seguridad.Permisos;
import com.nube.sistema_hoteles.entity.seguridad.Roles;

public interface IRolesService {
    Roles crearRol(Roles rol)  throws Exception;                       
    Roles actualizarRol(Roles rol);                  
    Roles obtenerRolPorId(Integer idRol);
    List<Roles> obtenerTodosLosRoles();
    void eliminarRol(Integer idRol);
    Roles crearRolSinPermiso(Roles rol) throws Exception;
    List<Permisos> obtenerPermisosPorRol(Integer idRol); 
    Roles asignarPermisosARol(Integer idRol, List<Integer> idPermisos);

}
