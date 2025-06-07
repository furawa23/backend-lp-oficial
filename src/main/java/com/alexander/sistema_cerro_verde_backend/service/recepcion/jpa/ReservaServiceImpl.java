package com.alexander.sistema_cerro_verde_backend.service.recepcion.jpa;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.alexander.sistema_cerro_verde_backend.entity.recepcion.Habitaciones;
import com.alexander.sistema_cerro_verde_backend.entity.recepcion.HabitacionesXReserva;
import com.alexander.sistema_cerro_verde_backend.entity.recepcion.Reservas;
import com.alexander.sistema_cerro_verde_backend.entity.recepcion.Salones;
import com.alexander.sistema_cerro_verde_backend.entity.recepcion.SalonesXReserva;
import com.alexander.sistema_cerro_verde_backend.entity.ventas.Clientes;
import com.alexander.sistema_cerro_verde_backend.repository.recepcion.HabitacionesRepository;
import com.alexander.sistema_cerro_verde_backend.repository.recepcion.HabitacionesReservaRepository;
import com.alexander.sistema_cerro_verde_backend.repository.recepcion.ReservasRepository;
import com.alexander.sistema_cerro_verde_backend.repository.recepcion.SalonesRepository;
import com.alexander.sistema_cerro_verde_backend.repository.recepcion.SalonesReservaRepository;
import com.alexander.sistema_cerro_verde_backend.service.recepcion.ReservasService;
import com.alexander.sistema_cerro_verde_backend.service.ventas.ClientesService;

import jakarta.persistence.EntityNotFoundException;

@Service
public class ReservaServiceImpl implements ReservasService {

    @Autowired
    private ReservasRepository repository;

    @Autowired
    private HabitacionesRepository habitacionesRepository;

    @Autowired
    private SalonesRepository salonesRepository;


    @Autowired
    private HabitacionesReservaRepository habitacionesReservaRepository;

    @Autowired
    private SalonesReservaRepository salonesReservaRepository;

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

    if (reserva.getSalonesXReserva() != null) {
        reserva.getSalonesXReserva().forEach(hr -> hr.setReserva(reserva));
    }

    return repository.save(reserva);
}


    @Override
    @Transactional(readOnly = true)
    public Optional<Reservas> buscarId(Integer id) {
        return repository.findById(id)
            .map(reserva -> {
                // Filtra habitacionesXReserva con estado = 1
                if (reserva.getHabitacionesXReserva() != null) {
                    reserva.setHabitacionesXReserva(
                        reserva.getHabitacionesXReserva()
                            .stream()
                            .filter(hr -> hr.getEstado() == 1)
                            .collect(Collectors.toList())
                    );
                }
                
                // Filtra salonesXReserva con estado = 1
                if (reserva.getSalonesXReserva() != null) {
                    reserva.setSalonesXReserva(
                        reserva.getSalonesXReserva()
                            .stream()
                            .filter(sr -> sr.getEstado() == 1)
                            .collect(Collectors.toList())
                    );
                }
                return reserva;
            });
    }

    @Override
    @Transactional
    public void eliminar(Integer id) {
        try {
            Reservas reserva = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Reserva no encontrada"));

            // Cambiar estado de habitaciones a disponible antes de eliminar las relaciones
            List<HabitacionesXReserva> habitaciones = habitacionesReservaRepository.findByReservaId(id);
            for (HabitacionesXReserva hr : habitaciones) {
                Habitaciones habitacion = hr.getHabitacion();
                habitacion.setEstado_habitacion("Disponible");  // Disponible
                habitacionesRepository.save(habitacion);
            }

            // Lo mismo si quieres para salones, si aplica:
            List<SalonesXReserva> salones = salonesReservaRepository.findByReservaId(id);
            for (SalonesXReserva sr : salones) {
                Salones salon = sr.getSalon();
                salon.setEstado_salon("Disponible"); // Disponible
                salonesRepository.save(salon);
            }

            // Eliminar relaciones después de actualizar estados
            habitacionesReservaRepository.deleteByReservaId(id);
            salonesReservaRepository.deleteByReservaId(id);

            // Eliminar lógicamente la reserva
            reserva.setEstado(0);
            repository.save(reserva);

        } catch (Exception e) {
            throw new RuntimeException("Error al eliminar la reserva: " + e.getMessage());
        }
    }

    @Override
    @Transactional
    public Reservas modificar(Reservas reserva) {
        if (reserva == null || reserva.getId_reserva() == null) {
            throw new IllegalArgumentException("La reserva no puede ser nula ni tener ID nulo");
        }
    
        return repository.findById(reserva.getId_reserva())
            .map(existente -> {
                // Actualiza campos simples
                existente.setComentarios(reserva.getComentarios());
                existente.setFecha_inicio(reserva.getFecha_inicio());
                existente.setFecha_fin(reserva.getFecha_fin());
                existente.setCliente(reserva.getCliente());
                existente.setEstado_reserva(reserva.getEstado_reserva());
                existente.setNro_persona(reserva.getNro_persona());
    
                return repository.save(existente);
            })
            .orElseThrow(() -> new EntityNotFoundException(
                "Reserva no encontrada con ID: " + reserva.getId_reserva()
            ));
    }
    
}
