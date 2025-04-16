package com.alexander.sistema_cerro_verde_backend.entity;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Data
@Table(name = "rolespermisos")
public class RolesPermisos {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long rolesPermisosId;

    @ManyToOne(fetch= FetchType.EAGER)
    private Roles rol;
    
    @ManyToOne
    private Permisos permisos;
}
