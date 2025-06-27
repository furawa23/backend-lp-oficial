package com.nube.sistema_hoteles.service.mantenimiento;

import java.util.List;
import java.util.Optional;

import com.nube.sistema_hoteles.entity.mantenimiento.Limpiezas;



public interface ILimpiezasService {

    List<Limpiezas> buscarTodos();
    Optional<Limpiezas> buscarPorId(Integer id);
    void registrar(Limpiezas limpiezas);
    void actualizar(Integer id, Limpiezas limpiezas);
    void eliminarPorId(Integer id);

}