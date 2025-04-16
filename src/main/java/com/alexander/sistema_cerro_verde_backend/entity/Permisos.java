package com.alexander.sistema_cerro_verde_backend.entity;
import java.util.LinkedHashSet;
import java.util.Set;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Data
@Table(name = "permisos")
public class Permisos {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long idPermisos;
    private String nombrePermiso;
    private boolean estado;
    @OneToMany(mappedBy = "permisos", cascade = CascadeType.ALL)
    @JsonIgnore
    private Set<RolesPermisos> rolesPermisos = new LinkedHashSet<>();
    
}
