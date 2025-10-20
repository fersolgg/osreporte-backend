// Controlador REST para exponer endpoints relacionados con Osticket
package com.tuxpan.soportesw.osreporte_backend.controller;

// Importaciones de clases DTO, servicios y utilidades
import com.tuxpan.soportesw.osreporte_backend.osticket.dto.StaffPorEstadoDTO;
import com.tuxpan.soportesw.osreporte_backend.osticket.dto.TicketsPorAnoDTO;
import com.tuxpan.soportesw.osreporte_backend.osticket.dto.TicketsPorDiaDTO;
import com.tuxpan.soportesw.osreporte_backend.osticket.dto.TicketsPorEstadoDTO;
import com.tuxpan.soportesw.osreporte_backend.osticket.dto.TicketsPorTipoActividadDTO;
import com.tuxpan.soportesw.osreporte_backend.osticket.dto.TicketsPorMesDTO;
import com.tuxpan.soportesw.osreporte_backend.osticket.services.OsticketService;
import com.tuxpan.soportesw.osreporte_backend.utils.MessageUtil;
import com.tuxpan.soportesw.osreporte_backend.utils.ApiResponse;
import com.tuxpan.soportesw.osreporte_backend.utils.DateRangeResult;
import com.tuxpan.soportesw.osreporte_backend.utils.DateRangeUtil;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.http.ResponseEntity;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.http.HttpStatus;
import java.util.List;

// Indica que esta clase es un controlador REST de Spring
@RestController
// Define el prefijo de las rutas para los endpoints de este controlador
@RequestMapping("/osticket")
public class OsticketController {

    // Servicio para la lógica de negocio de Osticket
    private final OsticketService service;
    // Utilidad para obtener mensajes internacionalizados
    private final MessageUtil messageUtil;

    // Constructor que inyecta las dependencias necesarias
    public OsticketController(OsticketService service, MessageUtil messageUtil) {
        this.service = service;
        this.messageUtil = messageUtil;
    }

    // Endpoint GET para obtener el listado de staff por estado
    @GetMapping("/listado-staff-por-estado")
    public ApiResponse<List<StaffPorEstadoDTO>> listadoStaffPorEstado(@RequestParam int estado) {
        // Llama al servicio para obtener los datos filtrados por estado
        List<StaffPorEstadoDTO> data = service.listadoStaffPorEstado(estado);
        // Obtiene el mensaje de éxito
        String msg = messageUtil.get("info.success");
        // Retorna la respuesta API con los datos y el mensaje
        return new ApiResponse<>(data, msg, null);
    }
      // Endpoint REST para consultar el reporte de tickets creados por día en un rango de fechas
    @GetMapping("/reportes/tickets-por-dia")
    public List<TicketsPorDiaDTO> getTicketsPorDia(
            @RequestParam String fechaInicio,
            @RequestParam String fechaFin) {
        try {
            DateRangeResult dr = DateRangeUtil.parseAndValidate(fechaInicio, fechaFin, null);
            // Pasamos los valores en formato Timestamp 'yyyy-MM-dd HH:mm:ss' que usan los repositorios
            String from = java.time.format.DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss").withZone(dr.getZone()).format(dr.getStartInstant());
            String to = java.time.format.DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss").withZone(dr.getZone()).format(dr.getEndInstant());
            return service.obtenerTicketsCreadosPorDia(from, to);
        } catch (IllegalArgumentException ex) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, ex.getMessage());
        }
    }

    // Endpoint REST para consultar el reporte de tickets por estado en un rango de fechas
    @GetMapping("/reportes/tickets-por-estado")
    public List<TicketsPorEstadoDTO> getTicketsPorEstado(
            @RequestParam String fechaInicio,
            @RequestParam String fechaFin) {
        try {
            DateRangeResult dr = DateRangeUtil.parseAndValidate(fechaInicio, fechaFin, null);
            String from = java.time.format.DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss").withZone(dr.getZone()).format(dr.getStartInstant());
            String to = java.time.format.DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss").withZone(dr.getZone()).format(dr.getEndInstant());
            return service.obtenerTicketsPorEstado(from, to);
        } catch (IllegalArgumentException ex) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, ex.getMessage());
        }
    }

        // Endpoint REST para consultar el reporte de tickets por tipo de actividad en un rango de fechas
        @GetMapping("/reportes/tickets-por-tipo-actividad")
        public List<TicketsPorTipoActividadDTO> getTicketsPorTipoActividad(
                @RequestParam String fechaInicio,
                @RequestParam String fechaFin) {
            try {
                DateRangeResult dr = DateRangeUtil.parseAndValidate(fechaInicio, fechaFin, null);
                String from = java.time.format.DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss").withZone(dr.getZone()).format(dr.getStartInstant());
                String to = java.time.format.DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss").withZone(dr.getZone()).format(dr.getEndInstant());
                return service.obtenerTicketsPorTipoActividad(from, to);
            } catch (IllegalArgumentException ex) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, ex.getMessage());
            }
        }

        // Endpoint REST para consultar el reporte de tickets por mes en un rango de fechas
        @GetMapping("/reportes/tickets-por-mes")
        public List<TicketsPorMesDTO> getTicketsPorMes(
                @RequestParam String fechaInicio,
                @RequestParam String fechaFin) {
            try {
                DateRangeResult dr = DateRangeUtil.parseAndValidate(fechaInicio, fechaFin, null);
                String from = java.time.format.DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss").withZone(dr.getZone()).format(dr.getStartInstant());
                String to = java.time.format.DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss").withZone(dr.getZone()).format(dr.getEndInstant());
                return service.obtenerTicketsPorMes(from, to);
            } catch (IllegalArgumentException ex) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, ex.getMessage());
            }
        }

        // Endpoint REST para consultar el reporte de tickets por año en un rango de fechas
        @GetMapping("/reportes/tickets-por-año")
        public List<TicketsPorAnoDTO> getTicketsPorAno(
                @RequestParam String fechaInicio,
                @RequestParam String fechaFin) {
            try {
                DateRangeResult dr = DateRangeUtil.parseAndValidate(fechaInicio, fechaFin, null);
                String from = java.time.format.DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss").withZone(dr.getZone()).format(dr.getStartInstant());
                String to = java.time.format.DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss").withZone(dr.getZone()).format(dr.getEndInstant());
                return service.obtenerTicketsPorAno(from, to);
            } catch (IllegalArgumentException ex) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, ex.getMessage());
            }
        }

}