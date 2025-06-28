package com.nube.sistema_hoteles.service.mantenimiento.jpa;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nube.sistema_hoteles.entity.mantenimiento.TipoIncidencia;
import com.nube.sistema_hoteles.repository.mantenimiento.TipoIncidenciaRepository;
import com.nube.sistema_hoteles.service.mantenimiento.ITipoIncidenciaService;


@Service
public class TipoIncidenciaService implements ITipoIncidenciaService{
    @Autowired
    private TipoIncidenciaRepository repoTipoIncidencia;

    @Override //buscar todos
    public List<TipoIncidencia> buscarTodos() {
        return repoTipoIncidencia.findAll();
    }

    @Override //Buscar por id
    public Optional<TipoIncidencia> buscarPorId(Integer id) {
        return repoTipoIncidencia.findById(id);
    }

    @Override //Registrar
    public void registrar(TipoIncidencia tipoincidencia) {
        repoTipoIncidencia.save(tipoincidencia);
    }

    @Override //Actualizar
    public void actualizar (Integer id, TipoIncidencia tipoincidencia) {
        TipoIncidencia metodoActualizar = repoTipoIncidencia.findById(id).orElse(null);
        metodoActualizar.setNombre(tipoincidencia.getNombre());
        repoTipoIncidencia.save(metodoActualizar);
    }

    @Override //Eliminar
    public void eliminarPorId(Integer id) {
        repoTipoIncidencia.deleteById(id);
    }
}