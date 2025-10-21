// DTO para estructurar el reporte de tickets agrupados por año
package com.tuxpan.soportesw.osreporte_backend.osticket.dto;

// Representa la respuesta del reporte de tickets creados por año
public class TicketsPorAnoDTO {
    
    // Año en formato string (ej: "2025")
    private String ano;
    
    // Cantidad total de tickets creados en el año
    private Long cantidadTickets;
    
    // Constructor por defecto requerido para el framework
    public TicketsPorAnoDTO() {
    }
    
    // Constructor con parámetros para facilitar la creación de instancias
    public TicketsPorAnoDTO(String ano, Long cantidadTickets) {
        this.ano = ano;
        this.cantidadTickets = cantidadTickets;
    }
    
    // Getter para obtener el año
    public String getAno() {
        return ano;
    }
    
    // Setter para establecer el año
    public void setAno(String ano) {
        this.ano = ano;
    }
    
    // Getter para obtener la cantidad de tickets
    public Long getCantidadTickets() {
        return cantidadTickets;
    }
    
    // Setter para establecer la cantidad de tickets
    public void setCantidadTickets(Long cantidadTickets) {
        this.cantidadTickets = cantidadTickets;
    }
    
    // Override del método toString para debugging y logging
    @Override
    public String toString() {
        return "TicketsPorAnoDTO{" +
                "ano='" + ano + '\'' +
                ", cantidadTickets=" + cantidadTickets +
                '}';
    }
}