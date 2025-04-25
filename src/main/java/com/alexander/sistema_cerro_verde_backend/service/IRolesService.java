package com.alexander.sistema_cerro_verde_backend.service;
import java.util.List;

import com.alexander.sistema_cerro_verde_backend.entity.Permisos;
import com.alexander.sistema_cerro_verde_backend.entity.Roles;

public interface IRolesService {
    Roles crearRol(Roles rol);                       
    Roles actualizarRol(Roles rol);                  
    Roles obtenerRolPorId(Integer idRol);
    List<Roles> obtenerTodosLosRoles();
    void eliminarRol(Integer idRol);
    Roles crearRolSinPermiso(Roles rol);
    List<Permisos> obtenerPermisosPorRol(Integer idRol); 
    Roles asignarPermisosARol(Integer idRol, List<Integer> idPermisos);

}
