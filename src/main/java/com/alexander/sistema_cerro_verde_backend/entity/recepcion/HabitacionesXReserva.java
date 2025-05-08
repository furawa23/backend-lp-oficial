package com.alexander.sistema_cerro_verde_backend.entity.recepcion;

import com.fasterxml.jackson.annotation.JsonBackReference;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name="habitaciones_x_reserva")
public class HabitacionesXReserva {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id_hab_reserv;

    @ManyToOne
    @JoinColumn(name = "id_habitacion")
    private Habitaciones habitacion;

    @ManyToOne
    @JoinColumn(name = "id_reserva")
    @JsonBackReference
    private Reservas reserva;

    private Integer estado;

    public Integer getId_hab_reserv() {
        return id_hab_reserv;
    }

    public void setId_hab_reserv(Integer id_hab_reserv) {
        this.id_hab_reserv = id_hab_reserv;
    }

    public Habitaciones getHabitacion() {
        return habitacion;
    }

    public void setHabitacion(Habitaciones habitacion) {
        this.habitacion = habitacion;
    }

    public Reservas getReserva() {
        return reserva;
    }

    public void setReserva(Reservas reserva) {
        this.reserva = reserva;
    }

    public Integer getEstado() {
        return estado;
    }

    public void setEstado(Integer estado) {
        this.estado = estado;
    }

    
    
}
