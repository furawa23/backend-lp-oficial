package com.nube.sistema_hoteles.repository.caja;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.nube.sistema_hoteles.entity.caja.Cajas;
import com.nube.sistema_hoteles.entity.reportes.CajaResumenDTO;
import com.nube.sistema_hoteles.entity.seguridad.Usuarios;

public interface CajasRepository extends JpaRepository<Cajas, Integer> {

    Optional<Cajas> findByEstadoCaja(String estadoCaja);

    List<Cajas> findAllByEstadoCaja(String estadoCaja);

    Optional<Cajas> findByUsuarioAndEstadoCaja(Usuarios usuario, String estadoCaja);

    Optional<Cajas> findByUsuario(Usuarios usuario);

    // 1) Resumen global (ingresos vs egresos)
    @Query("""
        SELECT new com.nube.sistema_hoteles.entity.reportes.CajaResumenDTO(
          tt.nombre,
          SUM(tc.montoTransaccion)
        )
        FROM com.nube.sistema_hoteles.entity.caja.TransaccionesCaja tc
        JOIN tc.tipo tt
        WHERE tc.estado = 1
          AND tc.fechaHoraTransaccion BETWEEN :desde AND :hasta
          AND tt.nombre IN :tipos
        GROUP BY tt.nombre
    """)
    List<CajaResumenDTO> fetchResumenCaja(
      @Param("desde") LocalDateTime desde,
      @Param("hasta") LocalDateTime hasta,
      @Param("tipos") List<String> tipos
    );

   


}
