package com.alexander.sistema_cerro_verde_backend.service.ventas;

import java.util.List;
import java.util.Optional;

import com.alexander.sistema_cerro_verde_backend.dto.ventas.VentaDTO;
import com.alexander.sistema_cerro_verde_backend.entity.ventas.Ventas;

public interface IVentaService {

    List<VentaDTO> buscarTodos (); //Listar todas las ventas
    
    Optional<VentaDTO> buscarPorId (Integer id); //Buscar venta por ID

    VentaDTO convertirDTO(Ventas venta);

    void guardar (Ventas venta); //Guardar venta

    void modificar (Ventas venta); //Modificar venta

    void eliminar (Integer id); //Eliminar venta

    String generarComprobante(Integer id); //Generar comprobante

    byte[] generarPdf(Integer id);
}
