package com.nube.sistema_hoteles.entity.reportes;

import java.math.BigDecimal;
import java.math.BigInteger;

public interface  SalonVentasDTO {
// alias “salonNombre” en SQL → getter getSalonNombre()
    String getSalonNombre();

    // alias “vecesAlquilado” en SQL → getter getVecesAlquilado()
    BigInteger getVecesAlquilado();

    // alias “totalRecaudado” en SQL → getter getTotalRecaudado()
    BigDecimal getTotalRecaudado();
}