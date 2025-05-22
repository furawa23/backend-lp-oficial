package com.alexander.sistema_cerro_verde_backend.service.recepcion.jpa;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alexander.sistema_cerro_verde_backend.entity.reservas.Clientes;
import com.alexander.sistema_cerro_verde_backend.repository.recepcion.ClientesRepository;
import com.alexander.sistema_cerro_verde_backend.repository.recepcion.ReservasRepository;
import com.alexander.sistema_cerro_verde_backend.service.recepcion.ClientesService;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;

@Service
public class ClientesServiceImpl implements ClientesService{
    @PersistenceContext
    private EntityManager entityManager;

    @Autowired
    private ClientesRepository repoClientes;

    @Autowired
    private ReservasRepository repoReservas;

    @Override
    public List<Clientes> buscarTodos() {
        return repoClientes.findAll();
    }

    @Override
    public List<Clientes> buscarActivos(){
        return repoClientes.findActive();
    }

    @Override
    public Optional<Clientes> buscarPorId(Integer id){
        return repoClientes.findById(id);
    }

    @Override
    @Transactional
    public void guardar(Clientes cliente){
        Optional<Clientes> existente = repoClientes.findByDniRucIgnoreCase(cliente.getDniRuc());
        if(existente.isPresent()){
            Clientes c = existente.get();
            if(c.getEstado() == 0){
                System.out.println(c.getDniRuc());
                c.setEstado(1);
                c.setNombre(cliente.getNombre());
                c.setCorreo(cliente.getCorreo());
                c.setPais(cliente.getPais());
                c.setTelefono(cliente.getTelefono());
                entityManager.merge(c);
            } else {
                repoClientes.save(cliente);
            }
        } else {
            cliente.setEstado(1);
            repoClientes.save(cliente);
        }
    }

    @Override
    public void modificar(Clientes cliente){
        repoClientes.save(cliente);
    }

    @Override
    public void eliminar(Integer id){
        if (repoReservas.existsReservasPendientesOConfirmadas(id)) {
            throw new IllegalStateException("No se puede eliminar: el cliente tiene reservas pendientes o confirmadas.");
        }

        repoClientes.deleteById(id);
    }
}
