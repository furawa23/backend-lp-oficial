package com.alexander.sistema_cerro_verde_backend.entity;

import java.util.HashSet;
import java.util.Set;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Data
@Table(name="roles")
public class Roles {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idRol;
    private String nombreRol;
    private String descripcion;
    private boolean estado;
    
    @OneToMany(cascade=CascadeType.ALL, fetch= FetchType.LAZY, mappedBy="rol")
    @JsonIgnore
    private Set<UsuarioRoles> usuarioRoles = new HashSet<>();

    @OneToMany(cascade=CascadeType.ALL, fetch=FetchType.EAGER, mappedBy="rol")
    @JsonIgnore 
    private Set<RolesPermisos> rolesPermisos= new HashSet<>();

 
}
