package com.nube.sistema_hoteles.service.ventas;

import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import reactor.core.publisher.Mono;

@Service
public class ApiCliente {
    WebClient web = WebClient.create();
    private final String TOKEN = "apis-token-16217.AT5XTgs7ZegVsB2bbdc1WtrGpJ6RMa3w";

    public ApiCliente(WebClient.Builder webClientBuilder) {
        this.web = webClientBuilder
            .baseUrl("https://api.apis.net.pe/v2")
            .build();
    }

    public String consumirApi(String dni) {
        Mono<String> respuesta = web.get()
            .uri(uriBuilder -> uriBuilder
                .path("/reniec/dni")
                .queryParam("numero", dni)
                .build()
            )
            .header("Authorization", "Bearer " + TOKEN) // Agrega el token en header
            .retrieve()
            .bodyToMono(String.class);

        return respuesta.block(); // bloquea hasta que obtenga respuesta
    }

    
}
