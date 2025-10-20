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
public class OsticketTicketsPorEstadoIntegrationTest {

    @Autowired
    private OsticketQueryRepository repo;

    @PersistenceContext(unitName = "osticket")
    private EntityManager em;

    @Test
    @Transactional(transactionManager = "osticketTransactionManager")
    public void reportTicketsPorEstado_countsPerStatus() {
        // Limpiar y crear esquema mínimo
        em.createNativeQuery("DROP TABLE IF EXISTS ost_ticket").executeUpdate();
        em.createNativeQuery("DROP TABLE IF EXISTS ost_ticket_status").executeUpdate();

        em.createNativeQuery("CREATE TABLE ost_ticket_status (id INT PRIMARY KEY, name VARCHAR(100))").executeUpdate();
        em.createNativeQuery("CREATE TABLE ost_ticket (id INT PRIMARY KEY, created TIMESTAMP, status_id INT, source VARCHAR(50))").executeUpdate();

        // Insertar estados y tickets
        em.createNativeQuery("INSERT INTO ost_ticket_status (id, name) VALUES (1, 'Open')").executeUpdate();
        em.createNativeQuery("INSERT INTO ost_ticket_status (id, name) VALUES (2, 'Closed')").executeUpdate();

        em.createNativeQuery("INSERT INTO ost_ticket (id, created, status_id, source) VALUES (1, '2025-08-05 09:00:00', 1, 'Web')").executeUpdate();
        em.createNativeQuery("INSERT INTO ost_ticket (id, created, status_id, source) VALUES (2, '2025-08-06 10:00:00', 2, 'Email')").executeUpdate();
        em.createNativeQuery("INSERT INTO ost_ticket (id, created, status_id, source) VALUES (3, '2025-08-06 11:30:00', 2, 'Phone')").executeUpdate();

        // Usar rango real observado en la BD: 2025-06-01 .. 2025-09-22
        List<Object[]> raw = repo.getTicketsPorEstado("2025-06-01 00:00:00", "2025-09-22 23:59:59");

        assertNotNull(raw);
        assertFalse(raw.isEmpty(), "Debe retornar al menos un estado con cantidad");

        // Buscar que 'Open' tenga 1 y 'Closed' tenga 2
        boolean openFound = raw.stream().anyMatch(r -> "Open".equals(r[0]) && ((Number) r[1]).intValue() == 1);
        boolean closedFound = raw.stream().anyMatch(r -> "Closed".equals(r[0]) && ((Number) r[1]).intValue() == 2);

        assertTrue(openFound, "Open debe tener 1 ticket");
        assertTrue(closedFound, "Closed debe tener 2 tickets");
    }
}
