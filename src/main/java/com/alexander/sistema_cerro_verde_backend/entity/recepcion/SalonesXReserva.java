package com.alexander.sistema_cerro_verde_backend.entity.recepcion;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name="salones_x_reserva")
public class SalonesXReserva {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id_salon_reserv;
    
    @ManyToOne
    @JoinColumn(name = "id_salon")
    private Salones salon;

    @ManyToOne
    @JoinColumn(name = "id_reserva")
    private Reservas reserva;

    private Integer estado;

    public Integer getId_salon_reserv() {
        return id_salon_reserv;
    }

    public void setId_salon_reserv(Integer id_salon_reserv) {
        this.id_salon_reserv = id_salon_reserv;
    }

    public Salones getSalon() {
        return salon;
    }

    public void setSalon(Salones salon) {
        this.salon = salon;
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
