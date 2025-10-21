package com.tuxpan.soportesw.osreporte_backend.osticket.dto;

// DTO para mapear el resultado del reporte de tickets por estado
public class TicketsPorEstadoDTO {
    private String estado;
    private Long cantidad;

    // Constructor
    public TicketsPorEstadoDTO(String estado, Long cantidad) {
        this.estado = estado;
        this.cantidad = cantidad;
    }

    // Getters y setters
    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public Long getCantidad() {
        return cantidad;
    }

    public void setCantidad(Long cantidad) {
        this.cantidad = cantidad;
    }
}
