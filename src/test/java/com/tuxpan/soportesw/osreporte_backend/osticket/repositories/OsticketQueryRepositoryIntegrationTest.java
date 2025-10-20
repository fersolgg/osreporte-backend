package com.tuxpan.soportesw.osreporte_backend.osticket.repositories;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ActiveProfiles("test")
public class OsticketQueryRepositoryIntegrationTest {

    @Autowired
    private OsticketQueryRepository repo;

    @PersistenceContext(unitName = "osticket")
    private EntityManager em;

    @Test
    @Transactional(transactionManager = "osticketTransactionManager")
    public void getTicketsPorEstado_returnsCounts() {
    // Forzar esquema limpio: eliminar si existen y crear tablas mínimas
    em.createNativeQuery("DROP TABLE IF EXISTS ost_ticket_status").executeUpdate();
    em.createNativeQuery("DROP TABLE IF EXISTS ost_ticket").executeUpdate();

    em.createNativeQuery("CREATE TABLE ost_ticket_status (id INT PRIMARY KEY, name VARCHAR(100))").executeUpdate();
    em.createNativeQuery("CREATE TABLE ost_ticket (id INT PRIMARY KEY, created TIMESTAMP, status_id INT, source VARCHAR(50))").executeUpdate();

    em.createNativeQuery("INSERT INTO ost_ticket_status (id, name) VALUES (1, 'Open')").executeUpdate();
    em.createNativeQuery("INSERT INTO ost_ticket (id, created, status_id, source) VALUES (1, '2025-10-10 10:00:00', 1, 'Web')").executeUpdate();

        List<Object[]> results = repo.getTicketsPorEstado("2025-10-01 00:00:00", "2025-10-31 23:59:59");

        assertNotNull(results);
        assertFalse(results.isEmpty());
        // Buscar la tupla con 'Open'
        boolean found = results.stream().anyMatch(r -> "Open".equals(r[0]) && ((Number)r[1]).intValue() == 1);
        assertTrue(found, "Debe existir al menos 1 ticket con estado Open");
    }
}
