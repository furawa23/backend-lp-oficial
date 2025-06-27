package com.nube.sistema_hoteles.config;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.nube.sistema_hoteles.entity.seguridad.ApiClient;
import com.nube.sistema_hoteles.service.seguridad.ApiClientService;

import java.io.IOException;
import java.util.Collections;
import java.util.Optional;

@Order(1)
@Component
public class ExternalClientFilter extends OncePerRequestFilter {

    @Autowired
    private ApiClientService apiClientService;

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain)
            throws ServletException, IOException {

        String header = request.getHeader("Authorization");

        if (header != null && header.startsWith("Bearer ")) {
            String token = header.substring(7);

            Optional<ApiClient> clientOptional = apiClientService.findByAccessToken(token);

            if (clientOptional.isPresent() && clientOptional.get().getEstado() == 1) {
                ApiClient client = clientOptional.get();

                // Crea una autenticación válida con el cliente_id como nombre
                UsernamePasswordAuthenticationToken authentication =
                        new UsernamePasswordAuthenticationToken(
                                client.getClienteId(), null, Collections.emptyList());

                SecurityContextHolder.getContext().setAuthentication(authentication);
            }
        }

        filterChain.doFilter(request, response);
    }
}
