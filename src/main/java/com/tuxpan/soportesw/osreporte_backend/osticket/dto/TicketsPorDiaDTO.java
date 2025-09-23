package com.tuxpan.soportesw.osreporte_backend.osticket.dto;

// DTO para mapear el resultado del reporte de tickets creados por día
public class TicketsPorDiaDTO {
    private String fecha;
    private Long ticketsCreados;

    // Constructor
    public TicketsPorDiaDTO(String fecha, Long ticketsCreados) {
        this.fecha = fecha;
        this.ticketsCreados = ticketsCreados;
    }

    // Getters y setters
    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public Long getTicketsCreados() {
        return ticketsCreados;
    }

    public void setTicketsCreados(Long ticketsCreados) {
        this.ticketsCreados = ticketsCreados;
    }
}
