package com.alexander.sistema_cerro_verde_backend.repository.ventas;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;  // <-- importe añadido

import com.alexander.sistema_cerro_verde_backend.entity.reportes.ClienteFrecuenteDTO;
import com.alexander.sistema_cerro_verde_backend.entity.reportes.HabitacionVentasDTO;
import com.alexander.sistema_cerro_verde_backend.entity.reportes.PagoVentasDTO;
import com.alexander.sistema_cerro_verde_backend.entity.reportes.ProductoVentasDTO;
import com.alexander.sistema_cerro_verde_backend.entity.reportes.SalonVentasDTO;
import com.alexander.sistema_cerro_verde_backend.entity.reportes.VentaResumenDTO;
import com.alexander.sistema_cerro_verde_backend.entity.ventas.Ventas;

public interface VentasRepository extends JpaRepository<Ventas, Integer>{  
    
    // ——— Productos Más Vendidos
    @Query(value = """
        SELECT
          p.nombre              AS productoNombre,
          SUM(dv.cantidad)      AS cantidadVendida,
          SUM(dv.sub_total)     AS totalVendido
        FROM ventas v
        JOIN detalle_venta dv   ON v.id_venta = dv.id_venta
        JOIN productos p        ON dv.id_producto = p.id_producto
        WHERE
          v.fecha BETWEEN :desde AND :hasta
          AND v.estado = 1
          AND dv.estado = 1
        GROUP BY p.nombre
        ORDER BY cantidadVendida DESC
        """, nativeQuery = true)
    List<ProductoVentasDTO> findProductosMasVendidos(
        @Param("desde") String desde,
        @Param("hasta") String hasta
    );

    // 2) CLIENTES MÁS FRECUENTES
    @Query(value = """
        SELECT
            c.nombre                   AS clienteNombre,
            COUNT(v.id_venta)          AS cantidadCompras,
            SUM(v.total)               AS totalGastado
        FROM ventas v
        JOIN clientes c   ON v.id_cliente = c.id_cliente
        WHERE
            v.fecha BETWEEN :desde AND :hasta
            AND v.estado = 1
            AND c.estado = 1
        GROUP BY c.nombre
        ORDER BY cantidadCompras DESC
        """, nativeQuery = true)
    List<ClienteFrecuenteDTO> findClientesFrecuentes(
        @Param("desde") String desde,
        @Param("hasta") String hasta
    );

    // 3) HABITACIONES MÁS VENDIDAS
    @Query(value = """
        SELECT
            h.numero                           AS habitacionNumero,
            COUNT(vh.id_venta_habitacion)      AS vecesVendida,
            SUM(vh.sub_total)                  AS totalRecaudado
        FROM ventas v
        JOIN venta_habitacion vh   ON v.id_venta = vh.id_venta
        JOIN habitaciones h        ON vh.id_habitacion = h.id_habitacion
        WHERE
            v.fecha BETWEEN :desde AND :hasta
            AND v.estado = 1
            AND vh.sub_total IS NOT NULL
            AND h.estado = 1
        GROUP BY h.numero
        ORDER BY vecesVendida DESC
        """, nativeQuery = true)
    List<HabitacionVentasDTO> findHabitacionesMasVendidas(
        @Param("desde") String desde,
        @Param("hasta") String hasta
    );

    // ——— Salones Más Vendidos/Alquilados
    @Query(value = """
        SELECT
          s.nombre                 AS salonNombre,
          COUNT(vs.id_venta_salon) AS vecesAlquilado,
          SUM(vs.sub_total)        AS totalRecaudado
        FROM ventas v
        JOIN venta_salon vs   ON v.id_venta = vs.id_venta
        JOIN salones s       ON vs.id_salon = s.id_salon
        WHERE
          v.fecha BETWEEN :desde AND :hasta
          AND v.estado = 1
          AND s.estado = 1
        GROUP BY s.nombre
        ORDER BY vecesAlquilado DESC
        """, nativeQuery = true)
    List<SalonVentasDTO> findSalonesMasVendidos(
        @Param("desde") String desde,
        @Param("hasta") String hasta
    );

    // 5) MÉTODOS DE PAGO MÁS USADOS
    @Query(value = """
        SELECT
            m.nombre                                 AS metodoPago,
            COUNT(vmp.id_venta_metodo_pago)          AS vecesUsado,
            SUM(vmp.pago)                            AS totalRecibido
        FROM ventas v
        JOIN venta_metodo_pago vmp  ON v.id_venta = vmp.id_venta
        JOIN metodos_pago m         ON vmp.id_metodo_pago = m.id_metodo_pago
        WHERE
            v.fecha BETWEEN :desde AND :hasta
            AND v.estado = 1
            AND m.estado = 1
        GROUP BY m.nombre
        ORDER BY totalRecibido DESC
        """, nativeQuery = true)
    List<PagoVentasDTO> findMetodosPago(
        @Param("desde") String desde,
        @Param("hasta") String hasta
    );

    // —— VERSIÓN “RESUMEN GENÉRICO” PARA PDF / EXCEL ——

    // 6) PRODUCTOS MÁS VENDIDOS (RESUMEN GENÉRICO)
    @Query(value = """
    SELECT 
      p.nombre       AS nombre,
      SUM(dv.cantidad)  AS cantidad,
      SUM(dv.sub_total) AS total
    FROM ventas v
    JOIN detalle_venta dv ON v.id_venta = dv.id_venta
    JOIN productos p ON dv.id_producto = p.id_producto
    WHERE
      v.fecha BETWEEN :desde AND :hasta
      AND v.estado = 1
      AND dv.estado = 1
    GROUP BY p.nombre
    ORDER BY cantidad DESC
    """, nativeQuery = true)
List<VentaResumenDTO> findProductosMasVendidosResumen(
    @Param("desde") String desde,
    @Param("hasta") String hasta
);

    // 7) SALONES MÁS VENDIDOS (RESUMEN GENÉRICO)
    @Query(value = """
    SELECT 
      s.nombre                 AS nombre,
      COUNT(vs.id_venta_salon) AS cantidad,
      SUM(vs.sub_total)        AS total
    FROM ventas v
    JOIN venta_salon vs ON v.id_venta = vs.id_venta
    JOIN salones s      ON vs.id_salon = s.id_salon
    WHERE
      v.fecha BETWEEN :desde AND :hasta
      AND v.estado = 1
      AND s.estado = 1
    GROUP BY s.nombre
    ORDER BY cantidad DESC
    """, nativeQuery = true)
List<VentaResumenDTO> findSalonesMasVendidosResumen(
    @Param("desde") String desde,
    @Param("hasta") String hasta
);

    // 8) HABITACIONES MÁS VENDIDAS (RESUMEN GENÉRICO)
    @Query(value = """
        SELECT 
            CAST(h.numero AS CHAR)       AS nombre,
            COUNT(vh.id_venta_habitacion) AS cantidad,
            SUM(vh.sub_total)             AS total
        FROM ventas v
        JOIN venta_habitacion vh   ON v.id_venta = vh.id_venta
        JOIN habitaciones h        ON vh.id_habitacion = h.id_habitacion
        WHERE
            v.fecha BETWEEN :desde AND :hasta
            AND v.estado = 1
            AND vh.sub_total IS NOT NULL
            AND h.estado = 1
        GROUP BY h.numero
        ORDER BY cantidad DESC
        """, nativeQuery = true)
    List<VentaResumenDTO> findHabitacionesMasVendidasResumen(
        @Param("desde") String desde,
        @Param("hasta") String hasta
    );

    // 9) CLIENTES MÁS FRECUENTES (RESUMEN GENÉRICO)
    @Query(value = """
        SELECT 
            c.nombre           AS nombre,
            COUNT(v.id_venta)  AS cantidad,
            SUM(v.total)       AS total
        FROM ventas v
        JOIN clientes c  ON v.id_cliente = c.id_cliente
        WHERE
            v.fecha BETWEEN :desde AND :hasta
            AND v.estado = 1
            AND c.estado = 1
        GROUP BY c.nombre
        ORDER BY cantidad DESC
        """, nativeQuery = true)
    List<VentaResumenDTO> findClientesFrecuentesResumen(
        @Param("desde") String desde,
        @Param("hasta") String hasta
    );

    // 10) MÉTODOS DE PAGO MÁS USADOS (RESUMEN GENÉRICO)
    @Query(value = """
        SELECT 
            m.nombre                         AS nombre,
            COUNT(vmp.id_venta_metodo_pago)  AS cantidad,
            SUM(vmp.pago)                    AS total
        FROM ventas v
        JOIN venta_metodo_pago vmp  ON v.id_venta = vmp.id_venta
        JOIN metodos_pago m         ON vmp.id_metodo_pago = m.id_metodo_pago
        WHERE
            v.fecha BETWEEN :desde AND :hasta
            AND v.estado = 1
            AND m.estado = 1
        GROUP BY m.nombre
        ORDER BY cantidad DESC
        """, nativeQuery = true)
    List<VentaResumenDTO> findMetodosPagoResumen(
        @Param("desde") String desde,
        @Param("hasta") String hasta
    );
}