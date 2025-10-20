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
public class OsticketTicketsPorMesIntegrationTest {

    @Autowired
    private OsticketQueryRepository repo;

    @PersistenceContext(unitName = "osticket")
    private EntityManager em;

    @Test
    @Transactional(transactionManager = "osticketTransactionManager")
    public void reportTicketsPorMes_countsByMonth() {
        // Limpiar y crear esquema mínimo
        em.createNativeQuery("DROP TABLE IF EXISTS ost_ticket").executeUpdate();
        em.createNativeQuery("DROP TABLE IF EXISTS ost_ticket_status").executeUpdate();

        em.createNativeQuery("CREATE TABLE ost_ticket_status (id INT PRIMARY KEY, name VARCHAR(100))").executeUpdate();
        em.createNativeQuery("CREATE TABLE ost_ticket (id INT PRIMARY KEY, created TIMESTAMP, status_id INT, source VARCHAR(50))").executeUpdate();

        // Insertar tickets en distintos meses
        em.createNativeQuery("INSERT INTO ost_ticket (id, created, status_id, source) VALUES (1, '2025-06-10 09:00:00', 1, 'Web')").executeUpdate();
        em.createNativeQuery("INSERT INTO ost_ticket (id, created, status_id, source) VALUES (2, '2025-07-05 10:00:00', 1, 'Email')").executeUpdate();
        em.createNativeQuery("INSERT INTO ost_ticket (id, created, status_id, source) VALUES (3, '2025-07-15 11:00:00', 1, 'Phone')").executeUpdate();
        em.createNativeQuery("INSERT INTO ost_ticket (id, created, status_id, source) VALUES (4, '2025-08-20 12:00:00', 1, 'API')").executeUpdate();

        // Usar rango real observado en la BD: 2025-06-01 .. 2025-09-22
        List<Object[]> raw = repo.getTicketsCreadosPorMes("2025-06-01 00:00:00", "2025-09-22 23:59:59");

        assertNotNull(raw);
        assertFalse(raw.isEmpty(), "Debe retornar al menos un mes con conteo");

        boolean juneFound = raw.stream().anyMatch(r -> "2025-06".equals(r[0]) && ((Number) r[1]).intValue() == 1);
        boolean julyFound = raw.stream().anyMatch(r -> "2025-07".equals(r[0]) && ((Number) r[1]).intValue() == 2);
        boolean augFound = raw.stream().anyMatch(r -> "2025-08".equals(r[0]) && ((Number) r[1]).intValue() == 1);

        assertTrue(juneFound, "Debe contener 2025-06 con 1 ticket");
        assertTrue(julyFound, "Debe contener 2025-07 con 2 tickets");
        assertTrue(augFound, "Debe contener 2025-08 con 1 ticket");
    }
}
