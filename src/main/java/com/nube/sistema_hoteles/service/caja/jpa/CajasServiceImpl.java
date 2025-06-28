package com.nube.sistema_hoteles.service.caja.jpa;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nube.sistema_hoteles.entity.caja.Cajas;
import com.nube.sistema_hoteles.entity.seguridad.Usuarios;
import com.nube.sistema_hoteles.repository.caja.CajasRepository;
import com.nube.sistema_hoteles.service.caja.CajasService;

@Service
public class CajasServiceImpl implements CajasService {

    @Autowired
    private CajasRepository repository;

    @Override
    public List<Cajas> buscarTodos() {
        return repository.findAll();
    }

    @Override
    public List<Cajas> buscarCajasCerradas() {
        return repository.findAllByEstadoCaja("cerrada");
    }

    @Override
    public Optional<Cajas> buscarId(Integer id) {
        return repository.findById(id);
    }

    @Override
    public Optional<Cajas> buscarCajaAperturada() {
        return repository.findByEstadoCaja("abierta");
    }

    @Override
    public Cajas guardar(Cajas caja) {
        return repository.save(caja);
    }

    @Override
    public void eliminarId(Integer id) {
        repository.deleteById(id);
    }

    @Override
    public Optional<Cajas> buscarCajaAperturadaPorUsuario(Usuarios usuario) {
        return repository.findByUsuarioAndEstadoCaja(usuario, "abierta");
    }

    @Override
    public Optional<Cajas> buscarCajaPorUsuario(Usuarios usuario) {
        return repository.findByUsuario(usuario);
    }

    
    
}
