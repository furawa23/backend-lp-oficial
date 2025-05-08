package com.alexander.sistema_cerro_verde_backend.entity.recepcion;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table
public class Salones {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id_salon;
    private Double precio_diario;
    private Double precio_hora;
    private String nombre;
    private String estado_salon;
    private Integer estado;
    
    
    public Integer getId_salon() {
        return id_salon;
    }
    public void setId_salon(Integer id_salon) {
        this.id_salon = id_salon;
    }
    public Double getPrecio_diario() {
        return precio_diario;
    }
    public void setPrecio_diario(Double precio_diario) {
        this.precio_diario = precio_diario;
    }
    public Double getPrecio_hora() {
        return precio_hora;
    }
    public void setPrecio_hora(Double precio_hora) {
        this.precio_hora = precio_hora;
    }
    public String getNombre() {
        return nombre;
    }
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
    public String getEstado_salon() {
        return estado_salon;
    }
    public void setEstado_salon(String estado_salon) {
        this.estado_salon = estado_salon;
    }
    public Integer getEstado() {
        return estado;
    }
    public void setEstado(Integer estado) {
        this.estado = estado;
    }

    

}
