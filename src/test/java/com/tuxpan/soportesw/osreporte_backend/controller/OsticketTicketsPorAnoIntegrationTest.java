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
public class OsticketTicketsPorAnoIntegrationTest {

    @Autowired
    private OsticketQueryRepository repo;

    @PersistenceContext(unitName = "osticket")
    private EntityManager em;

    @Test
    @Transactional(transactionManager = "osticketTransactionManager")
    public void reportTicketsPorAno_countsByYear() {
        // Limpiar y crear esquema mínimo
        em.createNativeQuery("DROP TABLE IF EXISTS ost_ticket").executeUpdate();
        em.createNativeQuery("DROP TABLE IF EXISTS ost_ticket_status").executeUpdate();

        em.createNativeQuery("CREATE TABLE ost_ticket_status (id INT PRIMARY KEY, name VARCHAR(100))").executeUpdate();
        em.createNativeQuery("CREATE TABLE ost_ticket (id INT PRIMARY KEY, created TIMESTAMP, status_id INT, source VARCHAR(50))").executeUpdate();

        // Insertar tickets en distintos años
        em.createNativeQuery("INSERT INTO ost_ticket (id, created, status_id, source) VALUES (1, '2024-12-31 23:59:59', 1, 'Web')").executeUpdate();
        em.createNativeQuery("INSERT INTO ost_ticket (id, created, status_id, source) VALUES (2, '2025-01-01 00:00:00', 1, 'Email')").executeUpdate();
        em.createNativeQuery("INSERT INTO ost_ticket (id, created, status_id, source) VALUES (3, '2025-06-10 09:00:00', 1, 'Phone')").executeUpdate();
        em.createNativeQuery("INSERT INTO ost_ticket (id, created, status_id, source) VALUES (4, '2026-03-15 14:30:00', 1, 'API')").executeUpdate();

        // Consultar rango amplio para cubrir todos los inserts
        List<Object[]> raw = repo.getTicketsCreadosPorAno("2024-01-01 00:00:00", "2026-12-31 23:59:59");

        assertNotNull(raw);
        assertFalse(raw.isEmpty(), "Debe retornar al menos un año con conteo");

        boolean y2024 = raw.stream().anyMatch(r -> "2024".equals(r[0]) && ((Number) r[1]).intValue() == 1);
        boolean y2025 = raw.stream().anyMatch(r -> "2025".equals(r[0]) && ((Number) r[1]).intValue() == 2);
        boolean y2026 = raw.stream().anyMatch(r -> "2026".equals(r[0]) && ((Number) r[1]).intValue() == 1);

        assertTrue(y2024, "Debe contener 2024 con 1 ticket");
        assertTrue(y2025, "Debe contener 2025 con 2 tickets");
        assertTrue(y2026, "Debe contener 2026 con 1 ticket");
    }
}
