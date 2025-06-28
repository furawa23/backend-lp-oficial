package com.nube.sistema_hoteles.excepciones;

public class UsuarioYaRegistradoException extends RuntimeException {
    public UsuarioYaRegistradoException() {
        super("El nombre de usuario ya está en uso");
    }
}