package com.nube.sistema_hoteles.service.seguridad;

public interface  IPasswordResetService {
    public void enviarLinkRecuperacion(String email);
}
