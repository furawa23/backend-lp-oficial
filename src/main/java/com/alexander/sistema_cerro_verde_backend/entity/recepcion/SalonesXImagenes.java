package com.alexander.sistema_cerro_verde_backend.entity.recepcion;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name="salones_x_imagenes")
public class SalonesXImagenes {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id_salon_img;

    @ManyToOne
    @JoinColumn(name = "id_salon")
    private Salones salon;

    @ManyToOne
    @JoinColumn(name = "id_imagen")
    private Imagenes imagen;

    public Integer getId_salon_img() {
        return id_salon_img;
    }

    public void setId_salon_img(Integer id_salon_img) {
        this.id_salon_img = id_salon_img;
    }

    public Salones getSalon() {
        return salon;
    }

    public void setSalon(Salones salon) {
        this.salon = salon;
    }

    public Imagenes getImagen() {
        return imagen;
    }

    public void setImagen(Imagenes imagen) {
        this.imagen = imagen;
    }

    
}
