package com.alexander.sistema_cerro_verde_backend.entity.mantenimiento;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

import java.util.Date;

import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import com.alexander.sistema_cerro_verde_backend.entity.Sucursales;
import com.alexander.sistema_cerro_verde_backend.entity.recepcion.Habitaciones;
import com.alexander.sistema_cerro_verde_backend.entity.seguridad.Usuarios;
import com.fasterxml.jackson.annotation.JsonBackReference;

@Entity
@Table(name = "incidencias")
@SQLDelete(sql = "UPDATE incidencias SET estado = 0 WHERE id_incidencia = ?")
@Where(clause = "estado = 1")
public class Incidencias {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id_incidencia;
    private Date fecha_registro;
    private Date fecha_solucion;
    private String estado_incidencia;
    private String descripcion;
    private Integer estado = 1;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_sucursal")
    private Sucursales sucursal;

    @ManyToOne
    @JoinColumn(name = "id_habitacion")
    private Habitaciones habitacion;

    @ManyToOne
    @JoinColumn(name = "id_tipo_incidencia")
    private TipoIncidencia tipoIncidencia;

    @ManyToOne
    @JoinColumn(name = "id_usuario")
    private Usuarios usuario;

    @ManyToOne
    @JoinColumn(name = "id_area")
    private AreasHotel area;

    public Integer getId_incidencia() {
        return id_incidencia;
    }

    public void setId_incidencia(Integer id_incidencia) {
        this.id_incidencia = id_incidencia;
    }

    public Date getFecha_registro() {
        return fecha_registro;
    }

    public void setFecha_registro(Date fecha_registro) {
        this.fecha_registro = fecha_registro;
    }

    public Date getFecha_solucion() {
        return fecha_solucion;
    }

    public void setFecha_solucion(Date fecha_solucion) {
        this.fecha_solucion = fecha_solucion;
    }

    public String getEstado_incidencia() {
        return estado_incidencia;
    }

    public void setEstado_incidencia(String estado_incidencia) {
        this.estado_incidencia = estado_incidencia;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
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

    public Habitaciones getHabitacion() {
        return habitacion;
    }

    public void setHabitacion(Habitaciones habitacion) {
        this.habitacion = habitacion;
    }

    public TipoIncidencia getTipoIncidencia() {
        return tipoIncidencia;
    }

    public void setTipoIncidencia(TipoIncidencia tipoIncidencia) {
        this.tipoIncidencia = tipoIncidencia;
    }

    public Usuarios getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuarios usuario) {
        this.usuario = usuario;
    }

    public AreasHotel getArea() {
        return area;
    }

    public void setArea(AreasHotel area) {
        this.area = area;
    }

    @Override
    public String toString() {
        return "Incidencias [id_incidencia=" + id_incidencia + ", fecha_registro=" + fecha_registro
                + ", fecha_solucion=" + fecha_solucion + ", estado_incidencia=" + estado_incidencia + ", descripcion="
                + descripcion + ", estado=" + estado + ", sucursal=" + sucursal + ", habitacion=" + habitacion
                + ", tipoIncidencia=" + tipoIncidencia + ", usuario=" + usuario + ", area=" + area + "]";
    }


    
}