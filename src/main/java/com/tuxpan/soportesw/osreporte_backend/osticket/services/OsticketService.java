
/**
 * Interfaz para servicios de reportes de osticket.
 * Provee métodos para obtener reportes de tickets por fecha y cantidad por tipo de actividad.
 */
package com.tuxpan.soportesw.osreporte_backend.osticket.services;

import com.tuxpan.soportesw.osreporte_backend.osticket.dto.StaffPorEstadoDTO;
import java.util.List;

/**
 * Servicio de reportes para osticket.
 * <p>
 * Métodos:
 * <ul>
 *   <li>{@code getTicketsPorFecha}: Devuelve la cantidad de tickets creados agrupados por fecha.</li>
 *   <li>{@code getTipoActividadCantidad}: Devuelve la cantidad de tickets agrupados por tipo de actividad.</li>
 * </ul>
 */
public interface OsticketService {
   
    /**
     * Obtiene el staff filtrado por estado (activo/inactivo).
     * @param estado 1 para activo, 0 para inactivo
     * @return Lista de DTOs con datos del staff filtrado
     */
    List<StaffPorEstadoDTO> listadoStaffPorEstado(int estado);
}
