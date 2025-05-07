package com.alexander.sistema_cerro_verde_backend.excepciones;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(CorreoYaRegistradoException.class)
    public ResponseEntity<String> handleCorreoYaRegistrado(CorreoYaRegistradoException ex) {
        return ResponseEntity.status(HttpStatus.CONFLICT).body(ex.getMessage());
    }
    @ExceptionHandler(UsuarioYaRegistradoException.class)
    public ResponseEntity<String> handleUsuarioYaRegistrado(UsuarioYaRegistradoException ex) {
        return ResponseEntity.status(HttpStatus.CONFLICT).body(ex.getMessage());
    }
    @ExceptionHandler(NombreRolYaExisteException.class)
    public ResponseEntity<String> handleNombreRolYaExiste(NombreRolYaExisteException ex) {
        return ResponseEntity.status(HttpStatus.CONFLICT).body(ex.getMessage());
    }
}