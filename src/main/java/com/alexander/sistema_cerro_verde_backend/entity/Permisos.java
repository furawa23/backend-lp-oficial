package com.alexander.sistema_cerro_verde_backend.entity;
import java.util.LinkedHashSet;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "permisos")
public class Permisos {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idPermisos;
    private String nombrePermiso;
    private boolean estado;

    @OneToMany(mappedBy = "permisos", cascade = CascadeType.ALL)
    @JsonIgnore
    private Set<RolesPermisos> rolesPermisos = new LinkedHashSet<>();

     // Relación con Modulos (clave foránea)
    @ManyToOne
    @JoinColumn(name = "idModulo", nullable = false)  // 'idModulo' será la clave foránea
    private Modulos modulo;

    public Integer getIdPermisos() {
        return idPermisos;
    }
    public void setIdPermisos(Integer idPermisos) {
        this.idPermisos = idPermisos;
    }
    public String getNombrePermiso() {
        return nombrePermiso;
    }
    public void setNombrePermiso(String nombrePermiso) {
        this.nombrePermiso = nombrePermiso;
    }
    public boolean isEstado() {
        return estado;
    }
    public void setEstado(boolean estado) {
        this.estado = estado;
    }
    public Set<RolesPermisos> getRolesPermisos() {
        return rolesPermisos;
    }
    public void setRolesPermisos(Set<RolesPermisos> rolesPermisos) {
        this.rolesPermisos = rolesPermisos;
    }
    
    public Modulos getModulo() {
        return modulo;
    }

    public void setModulo(Modulos modulo) {
        this.modulo = modulo;
    }
    

}
