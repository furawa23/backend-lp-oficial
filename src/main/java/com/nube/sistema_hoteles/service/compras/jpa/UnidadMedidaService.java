package com.nube.sistema_hoteles.service.compras.jpa;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nube.sistema_hoteles.entity.compras.UnidadMedida;
import com.nube.sistema_hoteles.repository.compras.UnidadMedidaRepository;
import com.nube.sistema_hoteles.service.compras.IUnidadMedidaService;

@Service
public class UnidadMedidaService implements IUnidadMedidaService{

    @Autowired
    private UnidadMedidaRepository repoUnidad;
    
    @Override
    public List<UnidadMedida> buscarTodos(){
        return repoUnidad.findAll();
    }

    @Override
    public void guardar (UnidadMedida unidad){
        repoUnidad.save(unidad);
    }

    @Override
    public void modificar(UnidadMedida unidad){
        repoUnidad.save(unidad);
    }

    @Override
    public Optional<UnidadMedida> buscarId(Integer id){
        return repoUnidad.findById(id);
    }

    @Override
    public void eliminar(Integer id){
        repoUnidad.deleteById(id);
    }
}
