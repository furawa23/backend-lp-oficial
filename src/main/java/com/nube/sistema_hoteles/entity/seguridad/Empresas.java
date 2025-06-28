package com.nube.sistema_hoteles.entity.seguridad;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "empresas")
public class Empresas {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_empresa")
    private Integer id;
    private String logo;
    private String nombre;
    private String encargado;
    private String ruc;
    private String direccion;
    private Integer estado=1;
    
    public Integer getId() {
        return id;
    }
    public void setId(Integer id) {
        this.id = id;
    }
    public String getNombre() {
        return nombre;
    }
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
    public String getRuc() {
        return ruc;
    }
    public void setRuc(String ruc) {
        this.ruc = ruc;
    }
    public String getDireccion() {
        return direccion;
    }
    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }
    public Integer getEstado() {
        return estado;
    }
    public void setEstado(Integer estado) {
        this.estado = estado;
    }
    public String getLogo() {
        return logo;
    }
    public void setLogo(String logo) {
        this.logo = logo;
    }
    public String getEncargado() {
        return encargado;
    }
    public void setEncargado(String encargado) {
        this.encargado = encargado;
    }
    @Override
    public String toString() {
        return "Empresas [id=" + id + ", logo=" + logo + ", nombre=" + nombre + ", encargado=" + encargado + ", ruc="
                + ruc + ", direccion=" + direccion + ", estado=" + estado + "]";
    }
    
    
 
}
