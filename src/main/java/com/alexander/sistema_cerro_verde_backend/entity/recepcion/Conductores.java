package com.alexander.sistema_cerro_verde_backend.entity.recepcion;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table
public class Conductores {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id_conductor;
    private String nombre;
    private String dni;
    private String placa;
    private String modelo_vehiculo;
    private Integer estado;

    public Integer getId_conductor() {
        return id_conductor;
    }
    public void setId_conductor(Integer id_conductor) {
        this.id_conductor = id_conductor;
    }
    public String getNombre() {
        return nombre;
    }
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
    public String getDni() {
        return dni;
    }
    public void setDni(String dni) {
        this.dni = dni;
    }
    public String getPlaca() {
        return placa;
    }
    public void setPlaca(String placa) {
        this.placa = placa;
    }
    public String getModelo_vehiculo() {
        return modelo_vehiculo;
    }
    public void setModelo_vehiculo(String modelo_vehiculo) {
        this.modelo_vehiculo = modelo_vehiculo;
    }
    public Integer getEstado() {
        return estado;
    }
    public void setEstado(Integer estado) {
        this.estado = estado;
    }

    

    

}
