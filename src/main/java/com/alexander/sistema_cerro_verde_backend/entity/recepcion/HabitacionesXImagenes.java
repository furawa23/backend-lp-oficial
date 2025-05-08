package com.alexander.sistema_cerro_verde_backend.entity.recepcion;

import jakarta.persistence.*;

@Entity
@Table(name = "habitaciones_x_imagenes")
public class HabitacionesXImagenes {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id_hab_img;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_habitacion")
    private Habitaciones habitacion;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_imagen")
    private Imagenes imagen;

    // Getters y Setters
    public Integer getId_hab_img() {
        return id_hab_img;
    }

    public void setId_hab_img(Integer id_hab_img) {
        this.id_hab_img = id_hab_img;
    }

    public Habitaciones getHabitacion() {
        return habitacion;
    }

    public void setHabitacion(Habitaciones habitacion) {
        this.habitacion = habitacion;
    }

    public Imagenes getImagen() {
        return imagen;
    }

    public void setImagen(Imagenes imagen) {
        this.imagen = imagen;
    }
}
