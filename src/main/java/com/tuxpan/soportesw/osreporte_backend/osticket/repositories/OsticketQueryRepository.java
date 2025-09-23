// Repositorio personalizado para consultas nativas relacionadas con Osticket
package com.tuxpan.soportesw.osreporte_backend.osticket.repositories;

// Importaciones necesarias para el repositorio y manejo de entidades
import org.springframework.stereotype.Repository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import java.util.List;

// Indica que esta clase es un repositorio gestionado por Spring
@Repository
public class OsticketQueryRepository {
   
    // EntityManager para ejecutar consultas nativas en la base de datos Osticket
    @PersistenceContext(unitName = "osticket")
    private EntityManager entityManager;

    // Método para obtener el listado de staff filtrado por estado (activo/inactivo)
    public List<Object[]> listadoStaffPorEstado(int estado) {
        // Consulta SQL nativa que selecciona los campos principales del staff
        String sql = "SELECT "
            + "staff_id, "
            + "username, "
            + "firstname, "
            + "lastname, "
            + "email "
            + "FROM ost_staff "
            + "WHERE isactive = " + estado;
        // Ejecuta la consulta y retorna los resultados como lista de arreglos de objetos
        return entityManager.createNativeQuery(sql).getResultList();
    }
        // Consulta nativa para reporte de tickets creados por día en un rango de fechas
        // Recibe dos parámetros tipo String con el rango de fechas (formato 'YYYY-MM-DD HH:MM:SS')
        // Retorna una lista de arreglos de objetos: [fecha, cantidad de tickets]
    public List<Object[]> getTicketsCreadosPorDia(String fechaInicio, String fechaFin) {
        String sql = "SELECT DATE(t.created) AS fecha, COUNT(*) AS tickets_creados "
                   + "FROM ost_ticket t "
                   + "WHERE t.created BETWEEN '" + fechaInicio + "' AND '" + fechaFin + "' "
                   + "GROUP BY DATE(t.created) "
                   + "ORDER BY fecha";
        // Ejecuta la consulta y retorna los resultados como lista de arreglos de objetos
        return entityManager.createNativeQuery(sql).getResultList();
    }

    // Consulta nativa para reporte de tickets por estado en un rango de fechas
    public List<Object[]> getTicketsPorEstado(String fechaInicio, String fechaFin) {
        String sql = "SELECT ts.name AS estado, COUNT(*) AS cantidad "
                   + "FROM ost_ticket t "
                   + "LEFT JOIN ost_ticket_status ts ON ts.id = t.status_id "
                   + "WHERE t.created BETWEEN '" + fechaInicio + "' AND '" + fechaFin + "' "
                   + "GROUP BY ts.name";
        return entityManager.createNativeQuery(sql).getResultList();
    }
}
