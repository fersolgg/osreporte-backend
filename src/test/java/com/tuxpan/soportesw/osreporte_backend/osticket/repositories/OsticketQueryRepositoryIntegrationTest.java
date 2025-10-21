package com.tuxpan.soportesw.osreporte_backend.osticket.repositories;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
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
    // Ejecutar fixtures SQL idempotente desde src/test/resources/fixtures.sql
    try (InputStream in = getClass().getResourceAsStream("/fixtures.sql")) {
        if (in == null) {
            throw new IllegalStateException("fixtures.sql no encontrado en classpath");
        }
        String sql = new String(in.readAllBytes(), StandardCharsets.UTF_8);
        for (String stmt : sql.split(";")) {
            stmt = stmt.trim();
            if (!stmt.isEmpty()) {
                em.createNativeQuery(stmt).executeUpdate();
            }
        }
    } catch (Exception e) {
        throw new RuntimeException("Error ejecutando fixtures.sql", e);
    }

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
