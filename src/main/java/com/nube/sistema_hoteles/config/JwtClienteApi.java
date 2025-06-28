package com.nube.sistema_hoteles.config;

import com.nube.sistema_hoteles.entity.seguridad.ApiClient;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

import java.util.Date;
import javax.crypto.SecretKey;

import org.springframework.stereotype.Component;

@Component
public class JwtClienteApi {

    private static final SecretKey SECRET_KEY = Keys.hmacShaKeyFor("clave-super-segura-para-api-client-1234567890!".getBytes());

    public String generarJwt(ApiClient client) {
        long nowMillis = System.currentTimeMillis();
        long expMillis = nowMillis + 86400000; // 24 horas
        Date now = new Date(nowMillis);
        Date exp = new Date(expMillis);

        return Jwts.builder()
                .setSubject(client.getClienteId())
                .claim("email", client.getEmail())
                .claim("nombre", client.getNombres())
                .setIssuedAt(now)
                .setExpiration(exp)
                .signWith(SECRET_KEY, SignatureAlgorithm.HS256)
                .compact();
    }

}