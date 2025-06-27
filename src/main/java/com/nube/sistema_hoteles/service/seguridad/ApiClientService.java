package com.nube.sistema_hoteles.service.seguridad;

import java.util.Optional;

import com.nube.sistema_hoteles.entity.seguridad.ApiClient;

public interface ApiClientService {
    Optional<ApiClient> findByClienteId(String clienteId);
    Optional<ApiClient> findByAccessToken(String token);
    ApiClient save(ApiClient client);
    Optional<ApiClient> findByEmail(String email);
}
