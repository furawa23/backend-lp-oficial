
package com.nube.sistema_hoteles.controller.seguridad;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.nube.sistema_hoteles.entity.seguridad.Empresas;
import com.nube.sistema_hoteles.service.seguridad.administrable.EmpresasService;
import com.nube.sistema_hoteles.service.ventas.ApiCliente;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;


@CrossOrigin("*") 
@RestController
@RequestMapping("/hoteleria")
public class EmpresasController {
    
    @Autowired
    private EmpresasService empresaService;

    @Autowired
    private ApiCliente api;

    @GetMapping("/empresas")
    public List<Empresas> buscarTodos() {
        return empresaService.buscarTodos();
    }

    @PostMapping("/empresas")
    public ResponseEntity<?> guardar(
        @RequestParam("logo") MultipartFile logo,
        @RequestParam("nombre") String nombre,
        @RequestParam("encargado") String encargado,
        @RequestParam("ruc") String ruc,
        @RequestParam("direccion") String direccion
    ) {
        empresaService.guardar(logo, nombre, encargado, ruc, direccion);
        return ResponseEntity.ok(Map.of("mensaje", "Empresa registrada"));

    }

    @PutMapping("/empresas/{id}")
    public ResponseEntity<?> modificar(
        @PathVariable Integer id,
        @RequestParam("nombre") String nombre,
        @RequestParam("encargado") String encargado,
        @RequestParam("ruc") String ruc,
        @RequestParam("direccion") String direccion,
        @RequestParam(value = "logo", required = false) MultipartFile logo
    ) {
        try {
            empresaService.modificar(id, logo, nombre, encargado, ruc, direccion);
            return ResponseEntity.ok(Map.of("mensaje", "Empresa actualizada"));
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body("Error al actualizar empresa");
        }
    }


    @GetMapping("/empresas/{id}")
    public Optional<Empresas> buscarId(@PathVariable("id") Integer id) {
        return empresaService.buscarId(id);
    }

    @DeleteMapping("/empresas/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Integer id) {
        empresaService.eliminar(id);
        return ResponseEntity.noContent().build(); // 204 No Content
    }

    @SuppressWarnings("unchecked")
    @GetMapping("/empresaruc/{id}")
public ResponseEntity<?> buscarRuc(@PathVariable("id") String ruc) {
    try {
        String resultado = api.consumirApi(ruc);
        // convierte el string JSON a Map
        ObjectMapper mapper = new ObjectMapper();
        Map<String, Object> datos = mapper.readValue(resultado, Map.class);

        return ResponseEntity.ok(datos);
    } catch (Exception e) {
        return ResponseEntity.badRequest().body(Map.of("error", "RUC inválido o no encontrado"));
    }
}


}






