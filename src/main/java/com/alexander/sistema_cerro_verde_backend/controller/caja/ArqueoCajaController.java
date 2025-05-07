package com.alexander.sistema_cerro_verde_backend.controller.caja;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.alexander.sistema_cerro_verde_backend.entity.caja.ArqueosCaja;
import com.alexander.sistema_cerro_verde_backend.entity.caja.DenominacionDinero;
import com.alexander.sistema_cerro_verde_backend.entity.caja.DetalleArqueo;
import com.alexander.sistema_cerro_verde_backend.service.caja.ArqueosCajaService;
import com.alexander.sistema_cerro_verde_backend.service.caja.DenominacionDineroService;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

@CrossOrigin("*")
@RestController
@RequestMapping("/cerro-verde/caja/arqueo")
public class ArqueoCajaController {

    @Autowired
    private ArqueosCajaService arqueosCajaService;

    @Autowired
    private DenominacionDineroService denominacionDineroService;

    @GetMapping("/denominaciones")
    public ResponseEntity<List<DenominacionDinero>> obtenerDenominaciones() {
        List<DenominacionDinero> denominaciones = denominacionDineroService.buscarTodos();
        return ResponseEntity.ok(denominaciones);
    }

    @PostMapping("/crear")
    public ResponseEntity<?> realizarArqueo(@RequestBody List<DetalleArqueo> detalles) {

        ArqueosCaja arqueo = new ArqueosCaja();

        // Asociar los detalles con el arqueo
        for (DetalleArqueo detalle : detalles) {
            detalle.setArqueo(arqueo);
        }

        // Asignar todos los detalles al arqueo
        arqueo.setDetalles(detalles);

        // Guardar el arqueo y sus detalles
        ArqueosCaja arqueoGuardado = arqueosCajaService.guardar(arqueo);

        return ResponseEntity.ok(arqueoGuardado);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ArqueosCaja> obtenerArqueoPorId(@PathVariable Integer id) {
        
        Optional<ArqueosCaja> arqueo = arqueosCajaService.buscarId(id);
        
        if (arqueo.isPresent()) {
            ArqueosCaja arqueoExistente = arqueo.get();
            return ResponseEntity.ok(arqueoExistente);
        }

        return ResponseEntity.notFound().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<ArqueosCaja> actualizarArqueo(@PathVariable Integer id, @RequestBody ArqueosCaja arqueoActualizado) {
        Optional<ArqueosCaja> arqueoOpt = arqueosCajaService.buscarId(id);
    
        if (arqueoOpt.isPresent()) {
            ArqueosCaja arqueoExistente = arqueoOpt.get();
    
            // Actualizar propiedades principales del arqueo (como observaciones y caja)
            arqueoExistente.setObservaciones(arqueoActualizado.getObservaciones());
            arqueoExistente.setCaja(arqueoActualizado.getCaja());
    
            // Actualizar detalles (o eliminarlos/agregarlos si es necesario)
            for (DetalleArqueo detalleActualizado : arqueoActualizado.getDetalles()) {
                Optional<DetalleArqueo> detalleExistenteOpt = arqueoExistente.getDetalles().stream()
                    .filter(detalle -> detalle.getId().equals(detalleActualizado.getId())) // Verificar si el detalle ya existe
                    .findFirst();
    
                if (detalleExistenteOpt.isPresent()) {
                    // Detalle ya existe, solo actualizamos la cantidad
                    DetalleArqueo detalleExistente = detalleExistenteOpt.get();
                    detalleExistente.setCantidad(detalleActualizado.getCantidad());
                    // Si necesitas actualizar otros campos, lo puedes hacer aquí, por ejemplo:
                    // detalleExistente.setOtroCampo(detalleActualizado.getOtroCampo());
                } else {
                    // Si el detalle no existe, lo agregamos (esto también funciona para nuevos detalles)
                    detalleActualizado.setArqueo(arqueoExistente); // Asociar al arqueo actual
                    arqueoExistente.getDetalles().add(detalleActualizado);
                }
            }
    
            // Guardar el arqueo actualizado y sus detalles
            ArqueosCaja actualizado = arqueosCajaService.guardar(arqueoExistente);
            return ResponseEntity.ok(actualizado);
        }
    
        return ResponseEntity.notFound().build();
    }
    

}
    
