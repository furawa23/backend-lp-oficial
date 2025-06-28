package com.nube.sistema_hoteles.service.recepcion.jpa;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.nube.sistema_hoteles.entity.caja.TipoTransacciones;
import com.nube.sistema_hoteles.entity.caja.TransaccionesCaja;
import com.nube.sistema_hoteles.entity.mantenimiento.Limpiezas;
import com.nube.sistema_hoteles.entity.recepcion.CheckinCheckout;
import com.nube.sistema_hoteles.entity.recepcion.HabitacionesXReserva;
import com.nube.sistema_hoteles.entity.recepcion.SalonesXReserva;
import com.nube.sistema_hoteles.entity.seguridad.Sucursales;
import com.nube.sistema_hoteles.entity.ventas.VentaMetodoPago;
import com.nube.sistema_hoteles.repository.caja.CajasRepository;
import com.nube.sistema_hoteles.repository.caja.TransaccionesCajaRepository;
import com.nube.sistema_hoteles.repository.mantenimiento.LimpiezasRepository;
import com.nube.sistema_hoteles.repository.recepcion.CheckinCheckoutRepository;
import com.nube.sistema_hoteles.repository.recepcion.HabitacionesRepository;
import com.nube.sistema_hoteles.repository.recepcion.HabitacionesReservaRepository;
import com.nube.sistema_hoteles.repository.recepcion.ReservasRepository;
import com.nube.sistema_hoteles.repository.recepcion.SalonesRepository;
import com.nube.sistema_hoteles.repository.recepcion.SalonesReservaRepository;
import com.nube.sistema_hoteles.repository.ventas.VentasRepository;
import com.nube.sistema_hoteles.service.recepcion.CheckinCheckoutService;
import com.nube.sistema_hoteles.service.seguridad.administrable.SucursalesService;

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

    @Autowired
    private VentasRepository repoVenta;

    @Autowired
    private CajasRepository repoCaja;

    @Autowired
    private TransaccionesCajaRepository repoTransacciones;

    @Autowired
    private LimpiezasRepository repoLimpiezas;

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

                        // 1. Obtener venta y marcar como completada
                        var venta = reserva.getVentaXReserva().get(0).getVenta();
                        venta.setEstadoVenta("Completado");
                        repoVenta.save(venta);

                        // 2. Obtener caja abierta
                        var caja = repoCaja.findByEstadoCaja("abierta")
                                .orElseThrow(() -> new RuntimeException("No hay caja abierta"));

                        // 3. Obtener tipo de transacción ingreso
                        TipoTransacciones tipoIngreso = new TipoTransacciones();
                        tipoIngreso.setId(1); // 1 = ingreso

                        // 4. Por cada método de pago, registrar la diferencia como nueva transacción
                        for (VentaMetodoPago metodo : venta.getVentaMetodoPago()) {
                            double pagoActual = metodo.getPago();
                            int idMetodo = metodo.getMetodoPago().getIdMetodoPago();

                            List<TransaccionesCaja> transaccionesPrevias = repoTransacciones
                                    .findByVentaIdAndMetodoPagoIdAndTipoId(venta.getIdVenta(), 1, idMetodo);

                            double yaRegistrado = transaccionesPrevias.stream()
                                    .mapToDouble(TransaccionesCaja::getMontoTransaccion)
                                    .sum();

                            double diferencia = pagoActual - yaRegistrado;

                            if (diferencia > 0) {
                                TransaccionesCaja nueva = new TransaccionesCaja();
                                nueva.setMontoTransaccion(diferencia);
                                nueva.setFechaHoraTransaccion(new Date());
                                nueva.setCaja(caja);
                                nueva.setTipo(tipoIngreso);
                                nueva.setVenta(venta);
                                nueva.setMetodoPago(metodo.getMetodoPago());

                                repoTransacciones.save(nueva);

                                // Actualizar saldos en caja
                                caja.setSaldoTotal(caja.getSaldoTotal() + diferencia);

                                if ("Efectivo".equalsIgnoreCase(metodo.getMetodoPago().getNombre())) {
                                    caja.setSaldoFisico(caja.getSaldoFisico() + diferencia);
                                }

                                repoCaja.save(caja);
                            }
                        }

                        // 5. Cambiar estado de habitaciones
                        List<HabitacionesXReserva> habitaciones = habitacionesReservasRepository.findByReservaId(reserva.getId_reserva());
                        for (HabitacionesXReserva hr : habitaciones) {
                            hr.getHabitacion().setEstado_habitacion("Limpieza");
                            
                            Limpiezas limpieza = new Limpiezas();
                            limpieza.setEstado_limpieza("Pendiente");
                            limpieza.setHabitacion(hr.getHabitacion());
                            limpieza.setFecha_registro(new Date());
                            repoLimpiezas.save(limpieza);

                            habitacionRepository.save(hr.getHabitacion());
                        }

                        // 6. Cambiar estado de salones
                        List<SalonesXReserva> salones = salonesReservasRepository.findByReservaId(reserva.getId_reserva());
                        for (SalonesXReserva sr : salones) {
                            sr.getSalon().setEstado_salon("Disponible");

                            Limpiezas limpieza = new Limpiezas();
                            limpieza.setEstado_limpieza("Pendiente");
                            limpieza.setSalon(sr.getSalon());
                            limpieza.setFecha_registro(new Date());
                            repoLimpiezas.save(limpieza);

                            salonesRepository.save(sr.getSalon());
                        }

                        // 7. Guardar cambios finales
                        reservaRepository.save(reserva);
                    }

                    return repository.save(existente);
                })
                .orElseThrow(() -> new EntityNotFoundException(
                "CheckinCheckout no encontrado con ID: " + check.getId_check()));
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
