package com.nube.sistema_hoteles.service.ventas;

import java.util.List;
import java.util.Optional;

import com.nube.sistema_hoteles.entity.ventas.ComprobantePago;

public interface IComprobantePagoService {

    List<ComprobantePago> buscarTodos();

    Optional<ComprobantePago> buscarPorId(Integer id);

    void guardar(ComprobantePago comprobantePago);

    void modificar(ComprobantePago comprobantePago);

    void eliminar(Integer id);

    String generarSiguienteCorrelativo(String serie);
}
