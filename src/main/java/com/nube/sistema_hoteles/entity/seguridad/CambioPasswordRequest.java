package com.nube.sistema_hoteles.entity.seguridad;

public class CambioPasswordRequest {
    private String passwordActual;
    private String nuevaPassword;
    public String getPasswordActual() {
        return passwordActual;
    }
    public void setPasswordActual(String passwordActual) {
        this.passwordActual = passwordActual;
    }
    public String getNuevaPassword() {
        return nuevaPassword;
    }
    public void setNuevaPassword(String nuevaPassword) {
        this.nuevaPassword = nuevaPassword;
    }

    

}

