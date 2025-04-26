package com.alexander.sistema_cerro_verde_backend.entity.seguridad;

import com.fasterxml.jackson.annotation.JsonBackReference;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "usuarios_permisos")
public class UsuariosPermisos {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id_usuarios_permisos")
    private Integer idUsuariosPermisos;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_usuario")
    @JsonBackReference  // Bien aplicado
    private Usuarios usuarios;
    
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_permiso")
    private Permisos permisos;


    public Integer getIdUsuariosPermisos() {
        return idUsuariosPermisos;
    }

    public void setIdUsuariosPermisos(Integer idUsuariosPermisos) {
        this.idUsuariosPermisos = idUsuariosPermisos;
    }

    public Usuarios getUsuarios() {
        return usuarios;
    }

    public void setUsuarios(Usuarios usuarios) {
        this.usuarios = usuarios;
    }

    public Permisos getPermisos() {
        return permisos;
    }

    public void setPermisos(Permisos permisos) {
        this.permisos = permisos;
    }

}
