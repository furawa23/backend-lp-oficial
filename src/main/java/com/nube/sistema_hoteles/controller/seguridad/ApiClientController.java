package com.nube.sistema_hoteles.controller.seguridad;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import com.nube.sistema_hoteles.config.JwtClienteApi;
import com.nube.sistema_hoteles.entity.seguridad.ApiClient;
import com.nube.sistema_hoteles.service.seguridad.ApiClientService;

import java.security.Principal;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/cerro-verde/api-client")
@CrossOrigin("*")
public class ApiClientController {

    @Autowired
    private ApiClientService apiClientService;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Autowired
    private JwtClienteApi jwtApi;

    // 1. Registrar nuevo cliente
    @PostMapping("/registrar")
    public ResponseEntity<?> registrar(@RequestBody ApiClient apiClientRequest) {
        Optional<ApiClient> existing = apiClientService.findByEmail(apiClientRequest.getEmail());
        if (existing.isPresent()) {
            return ResponseEntity.badRequest().body(Map.of("error", "El email ya está registrado."));
        }
    
        // Generar clienteId automáticamente
        apiClientRequest.setClienteId(null);
    
        // Generar llave secreta en texto plano
        String rawSecretKey = UUID.randomUUID().toString().replace("-", "").substring(0, 16); // más simple, 16 chars
    
        // Codificar con BCrypt y guardar
        apiClientRequest.setLlave_secreta(passwordEncoder.encode(rawSecretKey));
        apiClientRequest.setAccessToken(null);
    
        ApiClient saved = apiClientService.save(apiClientRequest);
    
        // Responder con los datos del cliente + la llave secreta sin codificar (solo una vez)
        return ResponseEntity.ok(Map.of(
            "cliente_id", saved.getClienteId(),
            "llave_secreta", rawSecretKey
        ));
    }

    // 2. Generar token (access_token) si cliente_id y clave coinciden
    @PostMapping("/token")
    public ResponseEntity<?> generarToken(@RequestBody Map<String, String> request) {
        String clienteId = request.get("cliente_id");
        String llaveSecreta = request.get("llave_secreta");
    
        if (clienteId == null || llaveSecreta == null) {
            return ResponseEntity.badRequest().body(Map.of("error", "Faltan parámetros: cliente_id y/o llave_secreta."));
        }
    
        Optional<ApiClient> clientOptional = apiClientService.findByClienteId(clienteId);
    
        if (clientOptional.isEmpty()) {
            return ResponseEntity.status(401).body(Map.of("error", "Cliente no encontrado."));
        }
    
        ApiClient client = clientOptional.get();
        if (!passwordEncoder.matches(llaveSecreta, client.getLlave_secreta())) {
            return ResponseEntity.status(401).body(Map.of("error", "Llave secreta incorrecta."));
        }
    
        // Generar nuevo token (puedes usar UUID o JWT, según prefieras)
        String jwt = this.jwtApi.generarJwt(client);
        client.setAccessToken(jwt);        
        apiClientService.save(client);
    
        return ResponseEntity.ok(Map.of("access_token", jwt));
    }    
    
    // 3. Consultar perfil del cliente autenticado
    @GetMapping("/perfil")
    public ResponseEntity<?> obtenerPerfil(Principal principal) {
        // Principal será el cliente_id establecido por ExternalClientFilter
        if (principal == null) {
            return ResponseEntity.status(401).body("No autenticado.");
        }

        Optional<ApiClient> client = apiClientService.findByClienteId(principal.getName());
        return client.<ResponseEntity<?>>map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.status(404).body("Cliente no encontrado."));
    }
    
}
