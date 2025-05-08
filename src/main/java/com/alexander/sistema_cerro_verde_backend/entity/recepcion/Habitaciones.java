package com.alexander.sistema_cerro_verde_backend.entity.recepcion;

import com.alexander.sistema_cerro_verde_backend.entity.Sucursales;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table
public class Habitaciones {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id_habitacion;
    private Integer numero;
    private Integer piso;
    private String estado_habitacion;
    private Integer estado;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_sucursal")
    private Sucursales sucursal;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_tipo_habitacion")
    private TipoHabitacion tipo_habitacion;

    public Integer getId_habitacion() {
        return id_habitacion;
    }

    public void setId_habitacion(Integer id_habitacion) {
        this.id_habitacion = id_habitacion;
    }

    public Integer getNumero() {
        return numero;
    }

    public void setNumero(Integer numero) {
        this.numero = numero;
    }

    

    public String getEstado_habitacion() {
        return estado_habitacion;
    }

    public void setEstado_habitacion(String estado_habitacion) {
        this.estado_habitacion = estado_habitacion;
    }

    public Integer getEstado() {
        return estado;
    }

    public void setEstado(Integer estado) {
        this.estado = estado;
    }

 

    public Sucursales getSucursal() {
        return sucursal;
    }

    public void setSucursal(Sucursales sucursal) {
        this.sucursal = sucursal;
    }

    public TipoHabitacion getTipo_habitacion() {
        return tipo_habitacion;
    }

    public void setTipo_habitacion(TipoHabitacion tipo_habitacion) {
        this.tipo_habitacion = tipo_habitacion;
    }

    public Integer getPiso() {
        return piso;
    }

    public void setPiso(Integer piso) {
        this.piso = piso;
    }



    
}

