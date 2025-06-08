package com.alexander.sistema_cerro_verde_backend.entity.ventas;

import java.util.List;

import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "metodos_pago")
@SQLDelete(sql = "UPDATE metodos_pago SET estado = 0 WHERE id_metodo_pago = ?")
@SQLRestriction("estado = 1")
public class MetodosPago {
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name="id_metodo_pago")
    private Integer idMetodoPago;
    private String nombre;
    private Integer estado = 1;
    private Integer estadoMetodo;

    //Relación uno a Muchos con VentaMetodoPago
    @OneToMany(mappedBy="metodoPago", cascade=CascadeType.ALL)
    @JsonIgnore
    private List<VentaMetodoPago> ventaMetodoPago;

    public Integer getIdMetodoPago() {
        return idMetodoPago;
    }

    public void setIdMetodoPago(Integer idMetodoPago) {
        this.idMetodoPago = idMetodoPago;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Integer getEstado() {
        return estado;
    }

    public void setEstado(Integer estado) {
        this.estado = estado;
    }

    public List<VentaMetodoPago> getVentaMetodoPago() {
        return ventaMetodoPago;
    }

    public void setVentaMetodoPago(List<VentaMetodoPago> ventaMetodoPago) {
        this.ventaMetodoPago = ventaMetodoPago;
    }

    public Integer getEstadoMetodo() {
        return estadoMetodo;
    }

    public void setEstadoMetodo(Integer estadoMetodo) {
        this.estadoMetodo = estadoMetodo;
    }
}
