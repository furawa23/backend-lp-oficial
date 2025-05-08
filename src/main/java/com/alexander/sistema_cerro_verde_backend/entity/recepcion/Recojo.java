package com.alexander.sistema_cerro_verde_backend.entity.recepcion;

import java.time.LocalDateTime;


import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table
public class Recojo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id_recojo;
    private String destino;
    private LocalDateTime fecha_hora;

    @ManyToOne
    @JoinColumn(name = "id_reserva")
    private Reservas reserva;

    @ManyToOne
    @JoinColumn(name = "id_conductor")
    private Conductores conductor;

    public Integer getId_recojo() {
        return id_recojo;
    }
    public void setId_recojo(Integer id_recojo) {
        this.id_recojo = id_recojo;
    }
    public String getDestino() {
        return destino;
    }
    public void setDestino(String destino) {
        this.destino = destino;
    }
    public LocalDateTime getFecha_hora() {
        return fecha_hora;
    }
    public void setFecha_hora(LocalDateTime fecha_hora) {
        this.fecha_hora = fecha_hora;
    }

    

}
