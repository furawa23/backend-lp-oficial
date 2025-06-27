package com.nube.sistema_hoteles.entity.seguridad;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "api_client")
@SQLDelete(sql = "UPDATE api_client SET estado=0 WHERE id = ?")
@Where(clause="estado = 1")
public class ApiClient {
@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String nombres;
    private String apellidos;
    private String email;
    private String clienteId;
    private String llave_secreta;
    private String accessToken;
    private Integer estado = 1;
    
    public Integer getId() {
        return id;
    }
    public void setId(Integer id) {
        this.id = id;
    }
    public String getNombres() {
        return nombres;
    }
    public void setNombres(String nombres) {
        this.nombres = nombres;
    }
    public String getApellidos() {
        return apellidos;
    }
    public void setApellidos(String apellidos) {
        this.apellidos = apellidos;
    }
    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public String getClienteId() {
        return clienteId;
    }
    public void setClienteId(String clienteId) {
        String datos = nombres + apellidos + email;
        MessageDigest md = null;
        try{ md=MessageDigest.getInstance("SHA-256"); }
        catch(NoSuchAlgorithmException e){e.printStackTrace();}
        md.update(datos.getBytes());
        byte[] digest = md.digest();
        String result = new BigInteger(1,digest).toString(16).toLowerCase();
        clienteId = result;
        this.clienteId = clienteId;
    }
    public String getLlave_secreta() {
        return llave_secreta;
    }
    public void setLlave_secreta(String llave_secreta) {
        this.llave_secreta = llave_secreta;
    }
    public Integer getEstado() {
        return estado;
    }
    public void setEstado(Integer estado) {
        this.estado = estado;
    }     
    public String getAccessToken() {
        return accessToken;
    }
    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }
    @Override
    public String toString() {
        return "ApiClient [id=" + id + ", nombres=" + nombres + ", apellidos=" + apellidos + ", email="
                + email + ", clienteId=" + clienteId + ", llave_secreta=" + llave_secreta + ", accessToken="
                + accessToken + ", estado=" + estado + "]";
    }
    
}