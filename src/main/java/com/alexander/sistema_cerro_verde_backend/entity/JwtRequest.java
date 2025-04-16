package com.alexander.sistema_cerro_verde_backend.entity;
import lombok.AllArgsConstructor;
import lombok.Data;
@Data
@AllArgsConstructor
public class JwtRequest {
    private String username; 
    private String password;
}
