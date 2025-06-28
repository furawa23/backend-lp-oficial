package com.nube.sistema_hoteles.service.compras;

import java.util.List;
import java.util.Optional;

import com.nube.sistema_hoteles.dto.compras.MovimientoInventarioDTO;
import com.nube.sistema_hoteles.entity.compras.MovimientosInventario;

public interface IMovimientosInventarioService {
    List<MovimientoInventarioDTO> buscarTodos();

    void guardar(MovimientosInventario movimientoinventario);

    MovimientoInventarioDTO convertirDTO(MovimientosInventario movimiento);

    void modificar(MovimientosInventario movimientoinventario);

    Optional<MovimientoInventarioDTO> buscarId(Integer id_movimiento_inventario);

    void eliminar(Integer id_movimiento_inventario);
}