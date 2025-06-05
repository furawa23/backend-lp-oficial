package com.alexander.sistema_cerro_verde_backend.repository.compras;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.alexander.sistema_cerro_verde_backend.entity.compras.Compras;
import com.alexander.sistema_cerro_verde_backend.entity.reportes.ProductoReporteDTO;
import com.alexander.sistema_cerro_verde_backend.entity.reportes.ProveedorReporteDTO;

public interface ComprasRepository extends JpaRepository<Compras, Integer> {

    @Query("SELECT c FROM Compras c ORDER BY c.id_compra DESC")
    Optional<Compras> obtenerUltimaCompra();

    @Query(value = """
        SELECT 
            p.nombre                   AS productoNombre,
            SUM(dc.cantidad)           AS cantidadComprada,
            SUM(dc.subtotal)           AS totalGastado
        FROM compras c
        JOIN detalles_compra dc     ON c.id_compra = dc.id_compra
        JOIN productos p            ON dc.id_producto = p.id_producto
        WHERE 
            STR_TO_DATE(c.fecha_compra, '%Y-%m-%d') BETWEEN STR_TO_DATE(:desde, '%Y-%m-%d')
                                                        AND STR_TO_DATE(:hasta, '%Y-%m-%d')
            AND c.estado = 1              -- sólo compras “activas”
            AND dc.estado = 1             -- sólo detalles “activos”
        GROUP BY p.nombre
        ORDER BY cantidadComprada DESC
        """,
        nativeQuery = true)
        List<ProductoReporteDTO> findProductosMasCompradosNative(
            @Param("desde") String desde,
            @Param("hasta") String hasta
        );

    @Query(value = """
        SELECT
        prov.razon_social       AS proveedorNombre,
        COUNT(*)                AS cantidadFacturas,
        SUM(c.total)            AS totalGastado
        FROM compras c
        JOIN proveedores prov 
        ON c.ruc_proveedor = prov.ruc_proveedor
        WHERE STR_TO_DATE(c.fecha_compra, '%Y-%m-%d')
            BETWEEN STR_TO_DATE(:desde, '%Y-%m-%d')
                AND STR_TO_DATE(:hasta, '%Y-%m-%d')
        GROUP BY prov.razon_social
        ORDER BY cantidadFacturas DESC
        """, nativeQuery = true)
    List<ProveedorReporteDTO> findProveedoresMasCompradosNative(
        @Param("desde") String desde,
        @Param("hasta") String hasta
    );

}
