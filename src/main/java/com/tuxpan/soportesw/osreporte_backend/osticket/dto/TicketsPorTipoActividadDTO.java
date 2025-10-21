package com.tuxpan.soportesw.osreporte_backend.osticket.dto;

// DTO para mapear el resultado del reporte de tickets por tipo de actividad
public class TicketsPorTipoActividadDTO {
    private String tipoActividad;
    private Long cantidad;

    // Constructor
    public TicketsPorTipoActividadDTO(String tipoActividad, Long cantidad) {
        this.tipoActividad = tipoActividad;
        this.cantidad = cantidad;
    }

    // Getters y setters
    public String getTipoActividad() {
        return tipoActividad;
    }

    public void setTipoActividad(String tipoActividad) {
        this.tipoActividad = tipoActividad;
    }

    public Long getCantidad() {
        return cantidad;
    }

    public void setCantidad(Long cantidad) {
        this.cantidad = cantidad;
    }
}
