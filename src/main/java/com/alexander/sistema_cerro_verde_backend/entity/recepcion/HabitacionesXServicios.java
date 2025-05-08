package com.alexander.sistema_cerro_verde_backend.entity.recepcion;


import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name="habitaciones_x_servicios")
public class HabitacionesXServicios {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id_hab_serv;

    @ManyToOne
    @JoinColumn(name = "id_habitacion")
    private Habitaciones habitacion;

    @ManyToOne
    @JoinColumn(name = "id_servicio")
    private Servicios servicio;

    public Integer getId_hab_serv() {
        return id_hab_serv;
    }

    public void setId_hab_serv(Integer id_hab_serv) {
        this.id_hab_serv = id_hab_serv;
    }

    public Habitaciones getHabitacion() {
        return habitacion;
    }

    public void setHabitacion(Habitaciones habitacion) {
        this.habitacion = habitacion;
    }

    public Servicios getServicio() {
        return servicio;
    }

    public void setServicio(Servicios servicio) {
        this.servicio = servicio;
    }

    
}
