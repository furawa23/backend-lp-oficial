package com.alexander.sistema_cerro_verde_backend.entity.mantenimiento;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import com.alexander.sistema_cerro_verde_backend.entity.recepcion.Habitaciones;
import com.alexander.sistema_cerro_verde_backend.entity.recepcion.Salones;

@Entity
@Table(name = "limpiezas")
@SQLDelete(sql = "UPDATE limpiezas SET estado = 0 WHERE id_limpieza = ?")
@Where(clause = "estado = 1")
public class Limpiezas {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id_limpieza;
    private String fecha_hora_limpieza;
    private String observaciones;
    private Integer estado = 1;

    @ManyToOne
    @JoinColumn(name ="id_personal_limpieza")
    private PersonalLimpieza personal;

    @ManyToOne
    @JoinColumn(name = "id_habitacion")
    private Habitaciones habitacion;

    @ManyToOne
    @JoinColumn(name = "id_salon")
    private Salones salon;

    public Integer getId_limpieza() {
        return id_limpieza;
    }

    public void setId_limpieza(Integer id_limpieza) {
        this.id_limpieza = id_limpieza;
    }

    public String getFecha_hora_limpieza() {
        return fecha_hora_limpieza;
    }

    public void setFecha_hora_limpieza(String fecha_hora_limpieza) {
        this.fecha_hora_limpieza = fecha_hora_limpieza;
    }

    public String getObservaciones() {
        return observaciones;
    }

    public void setObservaciones(String observaciones) {
        this.observaciones = observaciones;
    }

    public Integer getEstado() {
        return estado;
    }

    public void setEstado(Integer estado) {
        this.estado = estado;
    }

    public PersonalLimpieza getPersonal() {
        return personal;
    }

    public void setPersonal(PersonalLimpieza personal) {
        this.personal = personal;
    }

    public Habitaciones getHabitacion() {
        return habitacion;
    }

    public void setHabitacion(Habitaciones habitacion) {
        this.habitacion = habitacion;
    }

    public Salones getSalon() {
        return salon;
    }

    public void setSalon(Salones salon) {
        this.salon = salon;
    }

    @Override
    public String toString() {
        return "Limpiezas [id_limpieza=" + id_limpieza + ", fecha_hora_limpieza=" + fecha_hora_limpieza
                + ", observaciones=" + observaciones + ", estado=" + estado + ", personal=" + personal + ", habitacion="
                + habitacion + ", salon=" + salon + "]";
    }
    
}
