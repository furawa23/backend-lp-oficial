package com.alexander.sistema_cerro_verde_backend.excepciones;

public class CorreoYaRegistradoException extends Exception{
    public CorreoYaRegistradoException(){
        super("El correo electronico ya exite en la base de datos, vuelva a intentar");
    }
    public CorreoYaRegistradoException(String mensaje){
        super(mensaje);
    }
}
