package com.alexander.sistema_cerro_verde_backend.service.recepcion.jpa;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.alexander.sistema_cerro_verde_backend.entity.recepcion.Reservas;
import com.alexander.sistema_cerro_verde_backend.entity.ventas.Clientes;
import com.alexander.sistema_cerro_verde_backend.repository.recepcion.HabitacionesReservaRepository;
import com.alexander.sistema_cerro_verde_backend.repository.recepcion.ReservasRepository;
import com.alexander.sistema_cerro_verde_backend.service.recepcion.ReservasService;
import com.alexander.sistema_cerro_verde_backend.service.ventas.ClientesService;

import jakarta.persistence.EntityNotFoundException;

@Service
public class ReservaServiceImpl implements ReservasService {

    @Autowired
    private ReservasRepository repository;

    @Autowired
    private HabitacionesReservaRepository habitacionesReservaRepository;

    @Autowired
    private ClientesService clientesService;

    @Override
    @Transactional(readOnly = true)
    public List<Reservas> buscarTodos() {
        return repository.findAll();
    }

    @Override
@Transactional
public Reservas guardar(Reservas reserva) {
    if (reserva.getCliente() != null && reserva.getCliente().getIdCliente() != null) {
        Clientes cliente = clientesService.buscarPorId(reserva.getCliente().getIdCliente()).orElse(null);
        reserva.setCliente(cliente);
    }

    // Asociar manualmente la reserva a cada habitación
    if (reserva.getHabitacionesXReserva() != null) {
        reserva.getHabitacionesXReserva().forEach(hr -> hr.setReserva(reserva));
    }

    return repository.save(reserva);
}


    @Override
    @Transactional(readOnly = true)
    public Optional<Reservas> buscarId(Integer id) {
        return repository.findById(id);
    }

    @Override
    @Transactional
    public void eliminar(Integer id) {
        try {
            // Buscar la reserva en la base de datos
            Reservas reserva = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Reserva no encontrada"));

            // Eliminar las relaciones de habitaciones asociadas a la reserva
            try {
                habitacionesReservaRepository.deleteByReservaId(id);
            } catch (Exception e) {
                throw new RuntimeException("Error al eliminar las relaciones: " + e.getMessage());
            }

            // Cambiar el estado de la reserva a 0 (eliminación lógica)
            reserva.setEstado(0);

            // Guardar la reserva con el nuevo estado
            repository.save(reserva);
        } catch (Exception e) {
            throw new RuntimeException("Error al eliminar la reserva: " + e.getMessage());
        }
    }



    @Override
    public Reservas modificar(Reservas reserva) {
    // 1. Validar que la habitación no sea nula
    if (reserva == null) {
        throw new IllegalArgumentException("La habitación no puede ser nula");
    }

    // 2. Verificar que el ID existe
    if (reserva.getId_reserva() == null) {
        throw new IllegalArgumentException("El ID de la habitación es requerido");
    }

    // 3. Comprobar si existe antes de actualizar
    return repository.findById(reserva.getId_reserva())
        .map(existente -> {
            existente.setComentarios(reserva.getComentarios());
            existente.setFecha_inicio(reserva.getFecha_inicio());
            existente.setFecha_fin(reserva.getFecha_fin());
            existente.setCliente(reserva.getCliente());
            return repository.save(existente);
        })
        .orElseThrow(() -> new EntityNotFoundException(
            "Salón no encontrado con ID: " + reserva.getId_reserva()
        ));
    }

}
