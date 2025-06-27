package com.nube.sistema_hoteles.service.seguridad.jpa;

import java.util.Optional;
import org.springframework.stereotype.Service;

import com.nube.sistema_hoteles.entity.seguridad.ApiClient;
import com.nube.sistema_hoteles.repository.seguridad.ApiClientRepository;
import com.nube.sistema_hoteles.service.seguridad.ApiClientService;

import org.springframework.beans.factory.annotation.Autowired;

@Service
public class ApiClientServiceImpl implements ApiClientService {

    @Autowired
    private ApiClientRepository apiClientRepository;

    @Override
    public Optional<ApiClient> findByClienteId(String clienteId) {
        return apiClientRepository.findByClienteId(clienteId);
    }

    @Override
    public Optional<ApiClient> findByAccessToken(String token) {
        return apiClientRepository.findByAccessToken(token);
    }

    @Override
    public ApiClient save(ApiClient client) {
        return apiClientRepository.save(client);
    }

    @Override
    public Optional<ApiClient> findByEmail(String email) {
    return apiClientRepository.findAll().stream()
        .filter(c -> c.getEmail().equalsIgnoreCase(email))
        .findFirst();
}

}
