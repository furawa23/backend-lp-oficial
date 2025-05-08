package com.alexander.sistema_cerro_verde_backend.service.recepcion.jpa;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.alexander.sistema_cerro_verde_backend.entity.recepcion.CheckinCheckout;
import com.alexander.sistema_cerro_verde_backend.repository.recepcion.CheckinCheckoutRepository;
import com.alexander.sistema_cerro_verde_backend.service.recepcion.CheckinCheckoutService;

@Service
public class CheckinCheckoutServiceImpl implements CheckinCheckoutService {

    @Autowired
    private CheckinCheckoutRepository repository;

    @Override
    @Transactional(readOnly = true)
    public List<CheckinCheckout> buscarTodos() {
        return repository.findAll();
    }

    @Override
    @Transactional
    public CheckinCheckout guardar(CheckinCheckout check) {
        return repository.save(check);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<CheckinCheckout> buscarId(Integer id) {
        return repository.findById(id);
    }

    @Override
    @Transactional
    public void eliminarId(Integer id) {
        repository.deleteById(id);
    }

}
