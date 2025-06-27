package com.nube.sistema_hoteles.service.mantenimiento;

import java.util.List;
import java.util.Optional;

import com.nube.sistema_hoteles.entity.mantenimiento.AreasHotel;



public interface IAreasHotelService {

    List<AreasHotel> buscarTodos();
    Optional<AreasHotel> buscarPorId(Integer id);
    void registrar(AreasHotel areashotel);
    void actualizar(Integer id, AreasHotel areashotel);
    void eliminarPorId(Integer id);

}