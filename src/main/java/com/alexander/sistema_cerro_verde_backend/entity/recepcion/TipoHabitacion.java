package com.alexander.sistema_cerro_verde_backend.entity.recepcion;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name="tipo_habitacion")
public class TipoHabitacion {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id_tipo_habitacion;
    private String nombre;
    private Double precio_publico;
    private Double precio_corporativo;
    private Integer estado;
    
    public Integer getId_tipo_habitacion() {
        return id_tipo_habitacion;
    }
    public void setId_tipo_habitacion(Integer id_tipo_habitacion) {
        this.id_tipo_habitacion = id_tipo_habitacion;
    }
    public String getNombre() {
        return nombre;
    }
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
    public Double getPrecio_publico() {
        return precio_publico;
    }
    public void setPrecio_publico(Double precio_publico) {
        this.precio_publico = precio_publico;
    }
    public Double getPrecio_corporativo() {
        return precio_corporativo;
    }
    public void setPrecio_corporativo(Double precio_corporativo) {
        this.precio_corporativo = precio_corporativo;
    }
    public Integer getEstado() {
        return estado;
    }
    public void setEstado(Integer estado) {
        this.estado = estado;
    }

    

}
