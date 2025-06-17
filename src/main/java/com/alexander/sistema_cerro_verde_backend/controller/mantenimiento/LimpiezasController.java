package com.alexander.sistema_cerro_verde_backend.controller.mantenimiento;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.alexander.sistema_cerro_verde_backend.entity.mantenimiento.Limpiezas;
import com.alexander.sistema_cerro_verde_backend.entity.recepcion.Habitaciones;
import com.alexander.sistema_cerro_verde_backend.entity.seguridad.Usuarios;
import com.alexander.sistema_cerro_verde_backend.service.mantenimiento.jpa.LimpiezasService;
import com.alexander.sistema_cerro_verde_backend.service.recepcion.HabitacionesService;
import com.alexander.sistema_cerro_verde_backend.service.seguridad.jpa.UsuariosService;

@RestController
@RequestMapping("/cerro-verde/limpiezas")
@CrossOrigin("*")
public class LimpiezasController {

    @Autowired
    private LimpiezasService serviceLimpiezas;

    @Autowired
    private UsuariosService serviceUsuario;

    @Autowired
    private HabitacionesService serviceHabitacion;

    @GetMapping("/ver")
    public List<Limpiezas> buscarTodos() {
        return serviceLimpiezas.buscarTodos();
    }

    @GetMapping("/limpiezas/{id}")
    public Optional<Limpiezas> buscarPorId(@PathVariable Integer id) {
        return serviceLimpiezas.buscarPorId(id);
    }

    @PostMapping("/registrar")
    public Limpiezas registrarLimpieza(@RequestBody Limpiezas limpieza) {
        // 🔒 Validación de usuario
        Usuarios usuarioReal = serviceUsuario.obtenerUsuarioPorId(
            limpieza.getUsuario().getIdUsuario()
        );

        // 🔒 Validación de habitación
        Habitaciones habitacionReal = serviceHabitacion.buscarId(
            limpieza.getHabitacion().getId_habitacion()
        ).orElseThrow(() -> new RuntimeException("Habitación no encontrada"));

        // 👇 Asignar las entidades persistentes
        limpieza.setUsuario(usuarioReal);
        limpieza.setHabitacion(habitacionReal);

        // ✅ Guardar limpieza
        serviceLimpiezas.registrar(limpieza);
        return limpieza;
    }

    @PostMapping("/actualizar/{id}")
    public void actualizar(@PathVariable Integer id, @RequestBody Limpiezas limpiezas) {
        // También puedes validar aquí si deseas actualizar usuario/habitación
        serviceLimpiezas.actualizar(id, limpiezas);
    }

    @GetMapping("/eliminar/{id}")
    public void eliminarPorId(@PathVariable Integer id) {
        serviceLimpiezas.eliminarPorId(id);
    }
}


