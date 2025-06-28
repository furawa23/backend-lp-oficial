package com.nube.sistema_hoteles.service.mantenimiento;

import java.util.List;
import java.util.Optional;

import com.nube.sistema_hoteles.entity.mantenimiento.TipoIncidencia;



public interface ITipoIncidenciaService {

    List<TipoIncidencia> buscarTodos();
    Optional<TipoIncidencia> buscarPorId(Integer id);
    void registrar(TipoIncidencia tipoincidencia);
    void actualizar(Integer id, TipoIncidencia tipoincidencia);
    void eliminarPorId(Integer id);

}