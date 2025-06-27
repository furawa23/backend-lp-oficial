package com.nube.sistema_hoteles.service.caja.jpa;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.nube.sistema_hoteles.entity.caja.Cajas;
import com.nube.sistema_hoteles.entity.caja.TipoTransacciones;
import com.nube.sistema_hoteles.entity.caja.TransaccionesCaja;
import com.nube.sistema_hoteles.repository.caja.CajasRepository;
import com.nube.sistema_hoteles.repository.caja.TipoTransaccionesRepository;
import com.nube.sistema_hoteles.repository.caja.TransaccionesCajaRepository;
import com.nube.sistema_hoteles.service.caja.TransaccionesCajaService;

@Service
public class TransaccionesCajaServiceImpl implements TransaccionesCajaService {

    @Autowired
    private TransaccionesCajaRepository repository;

    @Autowired
    private TipoTransaccionesRepository transaccionesRepository;

    @Autowired
    private CajasRepository cajaRepository;

    @Override
    @Transactional(readOnly = true)
    public List<TransaccionesCaja> buscarTodos() {
        return repository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<TransaccionesCaja> encontrarId(Integer id) {
        return repository.findById(id);
    }

    @Override
    @Transactional
    public TransaccionesCaja guardar(TransaccionesCaja transaccion) {
        Optional<Cajas> cajaAbierta = cajaRepository.findByEstadoCaja("abierta"); // Suponiendo que 1 significa "abierta"
        
        if (cajaAbierta.isPresent()) {
            return repository.save(transaccion);
        } else {
            throw new RuntimeException("No hay una caja abierta para realizar la transacción.");
        }
    }

    @Override
    @Transactional
    public void eliminarId(Integer id) {
        repository.deleteById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public List<TransaccionesCaja> buscarPorCaja(Cajas caja) {
        return repository.findByCaja(caja);
    }

    @Override
    public TipoTransacciones obtenerTipoPorId(Integer id) {
        Optional<TipoTransacciones> opt = transaccionesRepository.findById(id);
        TipoTransacciones tipoTransaccion = opt.get();
        return tipoTransaccion;
    }

}