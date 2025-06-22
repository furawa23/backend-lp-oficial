package com.alexander.sistema_cerro_verde_backend.service.recepcion.jpa;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.alexander.sistema_cerro_verde_backend.entity.Sucursales;
import com.alexander.sistema_cerro_verde_backend.entity.recepcion.CheckinCheckout;
import com.alexander.sistema_cerro_verde_backend.entity.recepcion.HabitacionesXReserva;
import com.alexander.sistema_cerro_verde_backend.entity.recepcion.SalonesXReserva;
import com.alexander.sistema_cerro_verde_backend.repository.recepcion.CheckinCheckoutRepository;
import com.alexander.sistema_cerro_verde_backend.repository.recepcion.HabitacionesRepository;
import com.alexander.sistema_cerro_verde_backend.repository.recepcion.HabitacionesReservaRepository;
import com.alexander.sistema_cerro_verde_backend.repository.recepcion.ReservasRepository;
import com.alexander.sistema_cerro_verde_backend.repository.recepcion.SalonesRepository;
import com.alexander.sistema_cerro_verde_backend.repository.recepcion.SalonesReservaRepository;
import com.alexander.sistema_cerro_verde_backend.service.administrable.SucursalesService;
import com.alexander.sistema_cerro_verde_backend.service.recepcion.CheckinCheckoutService;

import jakarta.persistence.EntityNotFoundException;

@Service
public class CheckinCheckoutServiceImpl implements CheckinCheckoutService {

    @Autowired
    private CheckinCheckoutRepository repository;

    @Autowired
    private HabitacionesReservaRepository habitacionesReservasRepository;

    @Autowired
    private HabitacionesRepository habitacionRepository;

    @Autowired
    private SalonesReservaRepository salonesReservasRepository;

    @Autowired
    private SalonesRepository salonesRepository;

    @Autowired
    private ReservasRepository reservaRepository;

    @Autowired
    private SucursalesService sucursalService;

    @Override
    @Transactional(readOnly = true)
    public List<CheckinCheckout> buscarTodos() {
        return repository.findAll();
    }

    @Override
    @Transactional
    public CheckinCheckout guardar(CheckinCheckout check) {
        if (check.getSucursal() != null && check.getSucursal().getId() != null) {
        Sucursales sucursal = sucursalService.buscarId(check.getSucursal().getId()).orElse(null);
        check.setSucursal(sucursal);
    }

        if (check == null || check.getReserva() == null) {
            throw new IllegalArgumentException("Datos incompletos para guardar el check-in");
        }

        var reserva = reservaRepository.findById(check.getReserva().getId_reserva())
            .orElseThrow(() -> new IllegalArgumentException("Reserva no encontrada"));

        if (!"pagada".equalsIgnoreCase(reserva.getEstado_reserva())) {
            throw new IllegalArgumentException("No se puede hacer check-in: la reserva no está pagada");
        }

        reserva.setEstado_reserva("Check-in");
        check.setReserva(reserva);

        return repository.save(check);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<CheckinCheckout> buscarId(Integer id) {
        return repository.findById(id);
    }

    @Override
    @Transactional
    public CheckinCheckout modificar(CheckinCheckout check) {
        if (check == null || check.getId_check() == null) {
            throw new IllegalArgumentException("Datos incompletos para modificar el check-in");
        }

        return repository.findById(check.getId_check())
            .map(existente -> {
                existente.setFecha_checkin(check.getFecha_checkin());
                existente.setFecha_checkout(check.getFecha_checkout());

                if (check.getFecha_checkout() != null) {
                    var reserva = existente.getReserva();
                    reserva.setEstado_reserva("Completada");

                    // Liberar habitaciones
                    List<HabitacionesXReserva> habsReservas = habitacionesReservasRepository.findByReservaId(reserva.getId_reserva());
                    for (HabitacionesXReserva hr : habsReservas) {
                        hr.getHabitacion().setEstado_habitacion("Limpieza");
                        habitacionRepository.save(hr.getHabitacion());
                    }

                    // Liberar salones
                    List<SalonesXReserva> salonesReservas = salonesReservasRepository.findByReservaId(reserva.getId_reserva());
                    for (SalonesXReserva sr : salonesReservas) {
                        sr.getSalon().setEstado_salon("Disponible");
                        salonesRepository.save(sr.getSalon());
                    }

                    reservaRepository.save(reserva);
                }

                return repository.save(existente);
            })
            .orElseThrow(() -> new EntityNotFoundException("CheckinCheckout no encontrado con ID: " + check.getId_check()));
    }

    @Override
    @Transactional
    public void eliminar(Integer id) {
        CheckinCheckout check = repository.findById(id)
            .orElseThrow(() -> new RuntimeException("Conductor no encontrado"));

        check.setEstado(0);
        repository.save(check);
    }
}
