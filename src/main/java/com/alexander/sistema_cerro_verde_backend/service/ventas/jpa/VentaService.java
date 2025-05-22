package com.alexander.sistema_cerro_verde_backend.service.ventas.jpa;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alexander.sistema_cerro_verde_backend.entity.ventas.Ventas;
import com.alexander.sistema_cerro_verde_backend.repository.recepcion.HabitacionesRepository;
import com.alexander.sistema_cerro_verde_backend.repository.ventas.ClientesRepository;
import com.alexander.sistema_cerro_verde_backend.repository.ventas.VentasRepository;
import com.alexander.sistema_cerro_verde_backend.service.ventas.IVentaService;

@Service
public class VentaService implements IVentaService {

    @Autowired
    private VentasRepository repoVenta;

    @Autowired
    private HabitacionesRepository repoHabitacion;

    @Autowired
    private ClientesRepository clienteRepository;

    @Override
    public List<Ventas> buscarTodos() {
        return repoVenta.findAll();
    }

    @Override
    public Optional<Ventas> buscarPorId(Integer id) {
        return repoVenta.findById(id);
    }

    @Override
    public void guardar(Ventas venta) {
        repoVenta.save(venta);
    }

    @Override
    public void modificar(Ventas venta) {
        repoVenta.save(venta);
    }

    @Override
    public void eliminar(Integer id) {
        repoVenta.deleteById(id);
    }
}
