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

}
