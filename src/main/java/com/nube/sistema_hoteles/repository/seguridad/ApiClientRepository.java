package com.nube.sistema_hoteles.repository.seguridad;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

import com.nube.sistema_hoteles.entity.seguridad.ApiClient;

public interface ApiClientRepository extends JpaRepository<ApiClient, Long> {
    Optional<ApiClient> findByAccessToken(String token);
    Optional<ApiClient> findByClienteId(String clienteId);
    Optional<ApiClient> findByEmail(String email);
}
