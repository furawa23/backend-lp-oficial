package com.alexander.sistema_cerro_verde_backend.service.recepcion.jpa;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.alexander.sistema_cerro_verde_backend.entity.caja.Cajas;
import com.alexander.sistema_cerro_verde_backend.entity.recepcion.Habitaciones;
import com.alexander.sistema_cerro_verde_backend.entity.recepcion.HabitacionesXReserva;
import com.alexander.sistema_cerro_verde_backend.entity.recepcion.Reservas;
import com.alexander.sistema_cerro_verde_backend.entity.recepcion.Salones;
import com.alexander.sistema_cerro_verde_backend.entity.recepcion.SalonesXReserva;
import com.alexander.sistema_cerro_verde_backend.entity.seguridad.Sucursales;
import com.alexander.sistema_cerro_verde_backend.entity.seguridad.Usuarios;
import com.alexander.sistema_cerro_verde_backend.entity.ventas.Clientes;
import com.alexander.sistema_cerro_verde_backend.entity.ventas.Ventas;
import com.alexander.sistema_cerro_verde_backend.entity.ventas.VentasXReservas;
import com.alexander.sistema_cerro_verde_backend.repository.caja.CajasRepository;
import com.alexander.sistema_cerro_verde_backend.repository.recepcion.HabitacionesRepository;
import com.alexander.sistema_cerro_verde_backend.repository.recepcion.HabitacionesReservaRepository;
import com.alexander.sistema_cerro_verde_backend.repository.recepcion.ReservasRepository;
import com.alexander.sistema_cerro_verde_backend.repository.recepcion.SalonesRepository;
import com.alexander.sistema_cerro_verde_backend.repository.recepcion.SalonesReservaRepository;
import com.alexander.sistema_cerro_verde_backend.repository.seguridad.UsuariosRepository;
import com.alexander.sistema_cerro_verde_backend.repository.ventas.ClientesRepository;
import com.alexander.sistema_cerro_verde_backend.repository.ventas.VentasRepository;
import com.alexander.sistema_cerro_verde_backend.service.caja.CajasService;
import com.alexander.sistema_cerro_verde_backend.service.caja.TransaccionesCajaService;
import com.alexander.sistema_cerro_verde_backend.service.recepcion.ReservasService;
import com.alexander.sistema_cerro_verde_backend.service.seguridad.administrable.SucursalesService;
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

    @Autowired
    private ClientesRepository clientesRepository;

    @Autowired
    private SucursalesService sucursalService;

    @Autowired
    private CajasRepository cajaRepository;

    @Autowired
    private TransaccionesCajaService transaccionesCajaService;

    @Autowired
    private CajasService cajasService;

    @Autowired
    private UsuariosRepository usuarioRepository;

    @Autowired
    private VentasRepository repoVenta;

    @Override
    @Transactional(readOnly = true)
    public List<Reservas> buscarTodos() {
        List<Reservas> reservas = repository.findAll();

        // Filtrar solo habitacionesXReserva activas
        for (Reservas r : reservas) {
            List<HabitacionesXReserva> activas = r.getHabitacionesXReserva().stream()
                    .filter(hxr -> hxr.getEstado() == 1)
                    .toList();
            r.setHabitacionesXReserva(activas);
        }

        return reservas;
    }

    @Override
    @Transactional
    public Reservas guardar(Reservas reserva) {

        if (reserva.getSucursal() != null && reserva.getSucursal().getId() != null) {
            Sucursales sucursal = sucursalService.buscarId(reserva.getSucursal().getId()).orElse(null);
            reserva.setSucursal(sucursal);
        }

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
                    // Obtener cliente existente
                    Integer idCliente = reserva.getCliente() != null ? reserva.getCliente().getIdCliente() : null;
                    if (idCliente == null) {
                        throw new IllegalArgumentException("La reserva debe tener un cliente válido");
                    }

                    Clientes clienteExistente = clientesRepository.findById(idCliente)
                            .orElseThrow(() -> new EntityNotFoundException("Cliente no encontrado con ID: " + idCliente));

                    // Actualizar campos simples
                    existente.setComentarios(reserva.getComentarios());
                    existente.setFecha_inicio(reserva.getFecha_inicio());
                    existente.setFecha_fin(reserva.getFecha_fin());
                    existente.setCliente(clienteExistente); // Usa el cliente cargado de DB
                    existente.setEstado_reserva(reserva.getEstado_reserva());
                    existente.setNro_persona(reserva.getNro_persona());

                    return repository.save(existente);
                })
                .orElseThrow(() -> new EntityNotFoundException(
                "Reserva no encontrada con ID: " + reserva.getId_reserva()
        ));
    }

    @Override
    @Transactional
    public void cancelar(Integer idReserva) {
        Reservas reserva = repository.findById(idReserva)
                .orElseThrow(() -> new RuntimeException("Reserva no encontrada"));

        if (!reserva.getEstado_reserva().equalsIgnoreCase("Pagada")) {
            throw new RuntimeException("Solo se puede cancelar una reserva ya pagada");
        }

        reserva.setEstado_reserva("Cancelada");
        repository.save(reserva);

        List<HabitacionesXReserva> habitaciones = habitacionesReservaRepository.findByReservaId(idReserva);
        for (HabitacionesXReserva hr : habitaciones) {
            Habitaciones habitacion = hr.getHabitacion();
            habitacion.setEstado_habitacion("Disponible");  // Disponible
            habitacionesRepository.save(habitacion);
        }

        List<SalonesXReserva> salones = salonesReservaRepository.findByReservaId(idReserva);
        for (SalonesXReserva sr : salones) {
            Salones salon = sr.getSalon();
            salon.setEstado_salon("Disponible"); // Disponible
            salonesRepository.save(salon);
        }

        // Eliminar relaciones después de actualizar estados
        habitacionesReservaRepository.deleteByReservaId(idReserva);
        salonesReservaRepository.deleteByReservaId(idReserva);

        VentasXReservas rel = reserva.getVentaXReserva().get(0);
        Ventas venta = rel.getVenta();

        double monto = venta.getTotal();

        Usuarios usuario = usuarioRepository.findByUsername(SecurityContextHolder.getContext().getAuthentication().getName());
        Cajas caja = cajasService.buscarCajaAperturadaPorUsuario(usuario)
                .orElseThrow(() -> new RuntimeException("No hay una caja aperturada para este usuario"));

        // Verifica si la venta tuvo un método de pago, en caso de que haya, se genera una transacción de egreso
        // Optional<VentaMetodoPago> metodoPago = venta.getVentaMetodoPago().stream()
        //         .filter(vmp -> "Efectivo".equalsIgnoreCase(vmp.getMetodoPago().getNombre()))
        //         .findFirst();
        // if (metodoPago.isPresent()) {
        //     monto = metodoPago.get().getPago();
        //     TransaccionesCaja egreso = new TransaccionesCaja();
        //     egreso.setMontoTransaccion(monto);
        //     egreso.setCaja(caja); // 🔹 este campo es obligatorio

        //     TipoTransacciones tipoEgreso = new TipoTransacciones();
        //     tipoEgreso.setId(2); // 2 = egreso
        //     egreso.setTipo(tipoEgreso);

        //     egreso.setFechaHoraTransaccion(new Date());
        //     transaccionesCajaService.guardar(egreso);
        // }

        // // Actualizar saldos de caja
        // caja.setSaldoFisico(caja.getSaldoFisico() - monto);
        // caja.setSaldoTotal(caja.getSaldoTotal() - monto);
        // cajaRepository.save(caja);

        //Pasar el estado de la venta a cancelada
        venta.setEstadoVenta("Cancelado");
        repoVenta.save(venta);
    }

}
