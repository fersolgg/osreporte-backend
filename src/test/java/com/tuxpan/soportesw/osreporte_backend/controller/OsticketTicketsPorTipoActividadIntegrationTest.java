package com.tuxpan.soportesw.osreporte_backend.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.transaction.annotation.Transactional;

import com.tuxpan.soportesw.osreporte_backend.osticket.repositories.OsticketQueryRepository;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ActiveProfiles("test")
public class OsticketTicketsPorTipoActividadIntegrationTest {

    @Autowired
    private OsticketQueryRepository repo;

    @PersistenceContext(unitName = "osticket")
    private EntityManager em;

    @Test
    @Transactional(transactionManager = "osticketTransactionManager")
    public void reportTicketsPorTipoActividad_countsBySource() {
        // Limpiar y crear esquema mínimo
        em.createNativeQuery("DROP TABLE IF EXISTS ost_ticket").executeUpdate();
        em.createNativeQuery("DROP TABLE IF EXISTS ost_ticket_status").executeUpdate();

        em.createNativeQuery("CREATE TABLE ost_ticket_status (id INT PRIMARY KEY, name VARCHAR(100))").executeUpdate();
        em.createNativeQuery("CREATE TABLE ost_ticket (id INT PRIMARY KEY, created TIMESTAMP, status_id INT, source VARCHAR(50))").executeUpdate();

        // Insertar datos con distintas fuentes
        em.createNativeQuery("INSERT INTO ost_ticket (id, created, status_id, source) VALUES (1, '2025-07-15 09:00:00', 1, 'Web')").executeUpdate();
        em.createNativeQuery("INSERT INTO ost_ticket (id, created, status_id, source) VALUES (2, '2025-07-16 10:30:00', 1, 'Email')").executeUpdate();
        em.createNativeQuery("INSERT INTO ost_ticket (id, created, status_id, source) VALUES (3, '2025-07-16 11:00:00', 1, 'Phone')").executeUpdate();
        em.createNativeQuery("INSERT INTO ost_ticket (id, created, status_id, source) VALUES (4, '2025-07-17 12:00:00', 1, 'API')").executeUpdate();
        em.createNativeQuery("INSERT INTO ost_ticket (id, created, status_id, source) VALUES (5, '2025-07-17 12:30:00', 1, 'Custom')").executeUpdate();

        // Usar rango real observado en la BD: 2025-06-01 .. 2025-09-22
        List<Object[]> raw = repo.getTicketsPorTipoActividad("2025-06-01 00:00:00", "2025-09-22 23:59:59");

        assertNotNull(raw);
        assertFalse(raw.isEmpty(), "Debe retornar al menos un tipo de actividad");

        // Según CASE en el repositorio, deberíamos ver las categorías traducidas
        boolean webFound = raw.stream().anyMatch(r -> "Consulta Web".equals(r[0]) && ((Number) r[1]).intValue() == 1);
        boolean emailFound = raw.stream().anyMatch(r -> "Consulta Email".equals(r[0]) && ((Number) r[1]).intValue() == 1);
        boolean phoneFound = raw.stream().anyMatch(r -> "Consulta Telefónica".equals(r[0]) && ((Number) r[1]).intValue() == 1);
        boolean apiFound = raw.stream().anyMatch(r -> "Integración API".equals(r[0]) && ((Number) r[1]).intValue() == 1);
        boolean otherFound = raw.stream().anyMatch(r -> r[0] != null && r[0].toString().startsWith("Otros:") && ((Number) r[1]).intValue() == 1);

        assertTrue(webFound, "Debe contener 'Consulta Web' con 1 ticket");
        assertTrue(emailFound, "Debe contener 'Consulta Email' con 1 ticket");
        assertTrue(phoneFound, "Debe contener 'Consulta Telefónica' con 1 ticket");
        assertTrue(apiFound, "Debe contener 'Integración API' con 1 ticket");
        assertTrue(otherFound, "Debe contener 'Otros: Custom' con 1 ticket");
    }
}
