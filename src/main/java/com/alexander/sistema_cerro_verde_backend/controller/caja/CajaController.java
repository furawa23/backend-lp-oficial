package com.alexander.sistema_cerro_verde_backend.controller.caja;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.alexander.sistema_cerro_verde_backend.entity.caja.Cajas;
import com.alexander.sistema_cerro_verde_backend.entity.seguridad.Usuarios;
import com.alexander.sistema_cerro_verde_backend.repository.seguridad.UsuariosRepository;
import com.alexander.sistema_cerro_verde_backend.service.caja.CajasService;

@CrossOrigin("*") 
@RestController
@RequestMapping("/cerro-verde/caja")
public class CajaController {

    @Autowired
    private CajasService serviceCaja;

    @Autowired
    private UsuariosRepository usuarioRepository;

    @GetMapping
    public ResponseEntity<?> verificarEstadoCaja() {
        Optional<Cajas> cajaAbierta = serviceCaja.buscarCajaAperturada();

        if (cajaAbierta.isPresent()) {
            return ResponseEntity.ok(cajaAbierta.get());
        } else {
            return ResponseEntity.ok("no_aperturada");
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> obtenerCajaPorId(@PathVariable Integer id) {
    Optional<Cajas> caja = serviceCaja.buscarId(id);

    if (caja.isPresent()) {
        return ResponseEntity.ok(caja.get());
    } else {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Caja no encontrada");
    }
}
    
    @GetMapping("/aperturada")
    public ResponseEntity<?> obtenerCajaAperturada() {
        return serviceCaja.buscarCajaAperturada()
                .map(caja -> ResponseEntity.ok(caja))
                .orElse(ResponseEntity.noContent().build());
    }
    
    @GetMapping("/historial")
    public ResponseEntity<List<Cajas>> obtenerHistorialCajasCerradas() {
        return ResponseEntity.ok(serviceCaja.buscarCajasCerradas());
    }

    @PostMapping("/aperturar")
    public ResponseEntity<?> aperturarCaja(@RequestBody Cajas cajaRequest) {
        if (serviceCaja.buscarCajaAperturada().isPresent()) {
            return ResponseEntity.badRequest().body("Ya hay una caja aperturada"); // ya hay una caja abierta
        }
    
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth.getName(); // asumiendo que el username es el login del usuario

        Usuarios usuario = usuarioRepository.findByUsername(username);
        if (usuario == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
    
        Cajas nuevaCaja = new Cajas();
        nuevaCaja.setMontoApertura(cajaRequest.getMontoApertura());
        nuevaCaja.setFechaApertura(new Date());
        nuevaCaja.setEstadoCaja("abierta");
        nuevaCaja.setUsuario(usuario);
        nuevaCaja.setSaldo(nuevaCaja.getMontoApertura());
    
        return ResponseEntity.ok(serviceCaja.guardar(nuevaCaja));
    }
    
    @PostMapping("/cerrar")
    public ResponseEntity<?> cerrarCajaActual(@RequestBody Double montoCierre) {
        Optional<Cajas> cajaAbierta = serviceCaja.buscarCajaAperturada();
        if (cajaAbierta.isEmpty()) {
            return ResponseEntity.badRequest().body("No hay una caja aperturada.");
        }

        Cajas caja = cajaAbierta.get();
        caja.setMontoCierre(caja.getSaldo());
        caja.setFechaCierre(new Date());
        caja.setEstadoCaja("cerrada");

        return ResponseEntity.ok(serviceCaja.guardar(caja));
    }


}
