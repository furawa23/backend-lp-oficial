package com.alexander.sistema_cerro_verde_backend.entity.seguridad;

import java.util.List;

import com.alexander.sistema_cerro_verde_backend.entity.mantenimiento.AreasHotel;
import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "sucursales")
public class Sucursales {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_sucursal")
    private Integer id;
    private String nombre;
    private String ciudad;
    private String provincia;
    private String departamento;
    private String direccion;
    private String telefono;
    private Integer estado=1;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_empresa")
    private Empresas empresa;

    @OneToMany(mappedBy = "sucursal")
    @JsonIgnore
    private List<AreasHotel> areasHotel;
    
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCiudad() {
        return ciudad;
    }

    public void setCiudad(String ciudad) {
        this.ciudad = ciudad;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public Empresas getEmpresa() {
        return empresa;
    }

    public void setEmpresa(Empresas empresa) {
        this.empresa = empresa;
    }

    public List<AreasHotel> getAreasHotel() {
        return areasHotel;
    }

    public void setAreasHotel(List<AreasHotel> areasHotel) {
        this.areasHotel = areasHotel;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDepartamento() {
        return departamento;
    }

    public void setDepartamento(String departamento) {
        this.departamento = departamento;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public Integer getEstado() {
        return estado;
    }

    public void setEstado(Integer estado) {
        this.estado = estado;
    }

    public String getProvincia() {
        return provincia;
    }

    public void setProvincia(String provincia) {
        this.provincia = provincia;
    }

    @Override
    public String toString() {
        return "Sucursales [id=" + id + ", nombre=" + nombre + ", ciudad=" + ciudad + ", provincia=" + provincia
                + ", departamento=" + departamento + ", direccion=" + direccion + ", telefono=" + telefono + ", estado="
                + estado + ", empresa=" + empresa + "]";
    }

   
    
}
