package com.alexander.sistema_cerro_verde_backend.entity;
import com.fasterxml.jackson.annotation.JsonBackReference;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "rolespermisos")
public class RolesPermisos {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer rolesPermisosId;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "rol_id")
    @JsonBackReference // Bien aplicado
    private Roles roles;
    
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "permiso_id")
    private Permisos permisos;

    public RolesPermisos() {}  // Constructor vacío


    public Integer getRolesPermisosId() {
        return rolesPermisosId;
    }

    public void setRolesPermisosId(Integer rolesPermisosId) {
        this.rolesPermisosId = rolesPermisosId;
    }

    public Roles getRoles() {
        return roles;
    }

    public void setRoles(Roles rol) {
        this.roles = rol;
    }

    public Permisos getPermisos() {
        return permisos;
    }

    public void setPermisos(Permisos permisos) {
        this.permisos = permisos;
    }

    
}
