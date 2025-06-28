package com.nube.sistema_hoteles.service.ventas;

import java.util.List;
import java.util.Optional;

import com.nube.sistema_hoteles.entity.ventas.MetodosPago;

public interface IMetodoPagoService {

    List<MetodosPago> buscarTodos (); //Listar todos los métodos de pago

    Optional<MetodosPago> buscarPorId (Integer id); //Buscar método de pago por el ID

    void registrar (MetodosPago metodo); //Registrar método de pago

    void modificar (MetodosPago metodo); //Modificar el método de pago

    void eliminar (Integer id); //Eliminar el método de pago por el ID
}
