// Implementación de la interfaz OsticketService
package com.tuxpan.soportesw.osreporte_backend.osticket.services;

// Importaciones de DTO, repositorio y utilidades de Spring
import com.tuxpan.soportesw.osreporte_backend.osticket.dto.StaffPorEstadoDTO;
import com.tuxpan.soportesw.osreporte_backend.osticket.dto.TicketsPorAnoDTO;
import com.tuxpan.soportesw.osreporte_backend.osticket.dto.TicketsPorDiaDTO;
import com.tuxpan.soportesw.osreporte_backend.osticket.dto.TicketsPorEstadoDTO;
import com.tuxpan.soportesw.osreporte_backend.osticket.dto.TicketsPorTipoActividadDTO;
import com.tuxpan.soportesw.osreporte_backend.osticket.dto.TicketsPorMesDTO;
import com.tuxpan.soportesw.osreporte_backend.osticket.repositories.OsticketQueryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

// Indica que esta clase es un servicio gestionado por Spring
@Service
public class OsticketServiceImpl implements OsticketService {

        // Inyección del repositorio para consultas nativas
        @Autowired
        private OsticketQueryRepository osticketQueryRepository;

        // Implementación del método para obtener staff filtrado por estado
        @Override
        @Transactional(transactionManager = "osticketTransactionManager", readOnly = true)
        public List<StaffPorEstadoDTO> listadoStaffPorEstado(int estado) {
                // Ejecuta la consulta nativa y transforma los resultados en DTOs
                List<Object[]> resultados = osticketQueryRepository.listadoStaffPorEstado(estado);
                return resultados.stream()
                                .map(obj -> new StaffPorEstadoDTO(
                                                obj[0] != null ? Long.valueOf(obj[0].toString()) : null,
                                                obj[1] != null ? obj[1].toString() : null,
                                                obj[2] != null ? obj[2].toString() : null,
                                                obj[3] != null ? obj[3].toString() : null,
                                                obj[4] != null ? obj[4].toString() : null))
                                .collect(Collectors.toList());
        }
         // Implementación para obtener el reporte de tickets creados por día en un rango de fechas
    @Override
    @Transactional(transactionManager = "osticketTransactionManager", readOnly = true)
    public List<TicketsPorDiaDTO> obtenerTicketsCreadosPorDia(String fechaInicio, String fechaFin) {
        List<Object[]> resultados = osticketQueryRepository.getTicketsCreadosPorDia(fechaInicio, fechaFin);
        List<TicketsPorDiaDTO> lista = new ArrayList<>();
        for (Object[] fila : resultados) {
            // fila[0] = fecha, fila[1] = cantidad de tickets
            lista.add(new TicketsPorDiaDTO(fila[0].toString(), ((Number)fila[1]).longValue()));
        }
        return lista;
    }
    // Implementación para obtener el reporte de tickets por estado en un rango de fechas
@Override
public List<TicketsPorEstadoDTO> obtenerTicketsPorEstado(String fechaInicio, String fechaFin) {
    List<Object[]> resultados = osticketQueryRepository.getTicketsPorEstado(fechaInicio, fechaFin);
    List<TicketsPorEstadoDTO> lista = new ArrayList<>();
    for (Object[] fila : resultados) {
        // fila[0] = estado, fila[1] = cantidad
        lista.add(new TicketsPorEstadoDTO(fila[0].toString(), ((Number)fila[1]).longValue()));
    }
    return lista;
}
        // Implementación para obtener el reporte de tickets por tipo de actividad en un rango de fechas
        @Override
        @Transactional(transactionManager = "osticketTransactionManager", readOnly = true)
        public List<TicketsPorTipoActividadDTO> obtenerTicketsPorTipoActividad(String fechaInicio, String fechaFin) {
            List<Object[]> resultados = osticketQueryRepository.getTicketsPorTipoActividad(fechaInicio, fechaFin);
            List<TicketsPorTipoActividadDTO> lista = new ArrayList<>();
            for (Object[] fila : resultados) {
                String tipoRaw = fila[0] != null ? fila[0].toString() : "Sin tipo";
                String tipoLimpio = limpiarTipoActividad(tipoRaw);
                lista.add(new TicketsPorTipoActividadDTO(
                    tipoLimpio,
                    fila[1] != null ? ((Number)fila[1]).longValue() : 0L
                ));
            }
            return lista;
        }
        // Implementación para obtener el reporte de tickets por mes en un rango de fechas
        @Override
        @Transactional(transactionManager = "osticketTransactionManager", readOnly = true)
        public List<TicketsPorMesDTO> obtenerTicketsPorMes(String fechaInicio, String fechaFin) {
            List<Object[]> resultados = osticketQueryRepository.getTicketsCreadosPorMes(fechaInicio, fechaFin);
            List<TicketsPorMesDTO> lista = new ArrayList<>();
            for (Object[] fila : resultados) {
                // fila[0] = mes, fila[1] = cantidad de tickets
                lista.add(new TicketsPorMesDTO(fila[0].toString(), ((Number)fila[1]).longValue()));
            }
            return lista;
        }
        
        // Implementación para obtener el reporte de tickets por año en un rango de fechas
        @Override
        @Transactional(transactionManager = "osticketTransactionManager", readOnly = true)
        public List<TicketsPorAnoDTO> obtenerTicketsPorAno(String fechaInicio, String fechaFin) {
            List<Object[]> resultados = osticketQueryRepository.getTicketsCreadosPorAno(fechaInicio, fechaFin);
            List<TicketsPorAnoDTO> lista = new ArrayList<>();
            for (Object[] fila : resultados) {
                // fila[0] = año, fila[1] = cantidad de tickets
                lista.add(new TicketsPorAnoDTO(fila[0].toString(), ((Number)fila[1]).longValue()));
            }
            return lista;
        }

        // Función para limpiar el nombre del tipo de actividad
        private String limpiarTipoActividad(String tipoActividad) {
            if (tipoActividad == null || tipoActividad.equals("Sin tipo")) {
                return "Sin tipo";
            }
            // Si el string tiene formato JSON, extrae el valor
            if (tipoActividad.contains(":") && tipoActividad.contains("{")) {
                int idx = tipoActividad.indexOf(":");
                int end = tipoActividad.indexOf("}", idx);
                if (idx > 0 && end > idx) {
                    String valor = tipoActividad.substring(idx + 2, end).replace("\"", "").trim();
                    return valor;
                }
            }
            return tipoActividad;
    }
}