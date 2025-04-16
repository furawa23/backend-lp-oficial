package com.alexander.sistema_cerro_verde_backend.service;
import java.util.List;
import com.alexander.sistema_cerro_verde_backend.entity.Roles;

public interface IRolesService {
    Roles crearRol(Roles rol);
    Roles actualizarRol(Roles rol);
    Roles obtenerRolPorId(Long idRol);
    List<Roles> obtenerTodosLosRoles();
    Roles editarRol(Roles rol);
    void eliminarRol(Long idRol);   
}
