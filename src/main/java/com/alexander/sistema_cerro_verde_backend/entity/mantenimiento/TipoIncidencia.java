package com.alexander.sistema_cerro_verde_backend.entity.mantenimiento;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

import java.util.List;

import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import com.alexander.sistema_cerro_verde_backend.entity.Sucursales;

@Entity
@Table(name = "tipo_incidencia")
@SQLDelete(sql = "UPDATE tipo_incidencia SET estado = 0 WHERE id_tipo_incidencia = ?")
@Where(clause = "estado = 1")
public class TipoIncidencia {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id_tipo_incidencia;
    private String nombre;
    private Integer estado = 1;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_sucursal")
    private Sucursales sucursal;

    @OneToMany(mappedBy = "tipoIncidencia")
    private List<Incidencias> incidencias;

    
    public Integer getId_tipo_incidencia() {
        return id_tipo_incidencia;
    }
    public void setId_tipo_incidencia(Integer id_tipo_incidencia) {
        this.id_tipo_incidencia = id_tipo_incidencia;
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
    @Override
    public String toString() {
        return "TipoIncidencia [id_tipo_incidencia=" + id_tipo_incidencia + ", nombre=" + nombre + ", estado=" + estado
                + "]";
    }
    public List<Incidencias> getIncidencias() {
        return incidencias;
    }
    public void setIncidencias(List<Incidencias> incidencias) {
        this.incidencias = incidencias;
    }
    
}