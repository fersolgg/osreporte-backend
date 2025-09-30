package com.tuxpan.soportesw.osreporte_backend.osticket.dto;

// DTO para mapear el resultado del reporte de tickets por mes
public class TicketsPorMesDTO {
    private String mes; // Ejemplo: "2025-09"
    private Long cantidad;

    // Constructor
    public TicketsPorMesDTO(String mes, Long cantidad) {
        this.mes = mes;
        this.cantidad = cantidad;
    }

    // Getters y setters
    public String getMes() {
        return mes;
    }

    public void setMes(String mes) {
        this.mes = mes;
    }

    public Long getCantidad() {
        return cantidad;
    }

    public void setCantidad(Long cantidad) {
        this.cantidad = cantidad;
    }
}