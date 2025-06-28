package com.nube.sistema_hoteles.excepciones;

public class CredencialesInvalidasException extends Exception {
    public CredencialesInvalidasException(){
        super("Los datos ingresados en el sistema son invalidos");
    }
    public CredencialesInvalidasException(String mensaje){
        super(mensaje);
    }

}
