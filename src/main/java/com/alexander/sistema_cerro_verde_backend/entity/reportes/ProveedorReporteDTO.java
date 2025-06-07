package com.alexander.sistema_cerro_verde_backend.entity.reportes;

public class ProveedorReporteDTO {
private String proveedorNombre;
    private Long cantidadFacturas;
    private Double totalGastado;

    public ProveedorReporteDTO(String proveedorNombre, Long cantidadFacturas, Double totalGastado) {
        this.proveedorNombre = proveedorNombre;
        this.cantidadFacturas = cantidadFacturas;
        this.totalGastado = totalGastado;
    }

    // Getters y setters

    public String getProveedorNombre() {
        return proveedorNombre;
    }

    public void setProveedorNombre(String proveedorNombre) {
        this.proveedorNombre = proveedorNombre;
    }

    public Long getCantidadFacturas() {
        return cantidadFacturas;
    }

    public void setCantidadFacturas(Long cantidadFacturas) {
        this.cantidadFacturas = cantidadFacturas;
    }

    public Double getTotalGastado() {
        return totalGastado;
    }

    public void setTotalGastado(Double totalGastado) {
        this.totalGastado = totalGastado;
    }
}