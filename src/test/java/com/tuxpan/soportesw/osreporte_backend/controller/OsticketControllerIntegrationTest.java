package com.tuxpan.soportesw.osreporte_backend.controller;

import com.tuxpan.soportesw.osreporte_backend.osticket.dto.TicketsPorDiaDTO;
import com.tuxpan.soportesw.osreporte_backend.osticket.repositories.OsticketQueryRepository;
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
public class OsticketControllerIntegrationTest {

    @Autowired
    private OsticketQueryRepository repo;

    @PersistenceContext(unitName = "osticket")
    private EntityManager em;

    @Test
    @Transactional(transactionManager = "osticketTransactionManager")
    public void reportTicketsPorDia_returnsDailyCounts() {
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

        // Insertar datos de ejemplo
        em.createNativeQuery("INSERT INTO ost_ticket_status (id, name) VALUES (1, 'Open')").executeUpdate();
        em.createNativeQuery("INSERT INTO ost_ticket (id, created, status_id, source) VALUES (1, '2025-07-10 08:15:00', 1, 'Web')").executeUpdate();
        em.createNativeQuery("INSERT INTO ost_ticket (id, created, status_id, source) VALUES (2, '2025-07-10 12:30:00', 1, 'Phone')").executeUpdate();

        // Llamar al repo directamente (el controlador ya formatea fechas hacia el service/repo)
    // Usar rango real observado en la BD: 2025-06-02 .. 2025-09-22
    List<Object[]> raw = repo.getTicketsCreadosPorDia("2025-06-01 00:00:00", "2025-09-22 23:59:59");

        assertNotNull(raw);
        assertFalse(raw.isEmpty(), "Debe retornar al menos un día con conteo");

        // Normalizar y verificar: el primer campo puede venir como java.sql.Date o java.sql.Timestamp
        boolean found = raw.stream().anyMatch(r -> {
            Object dateObj = r[0];
            String dateStr = null;
            if (dateObj instanceof java.sql.Date) {
                dateStr = dateObj.toString();
            } else if (dateObj instanceof java.sql.Timestamp) {
                dateStr = ((java.sql.Timestamp) dateObj).toLocalDateTime().toLocalDate().toString();
            } else if (dateObj != null) {
                // Fallback: tomar la parte de fecha si viene como String con hora
                String s = dateObj.toString();
                dateStr = s.split(" ")[0];
            }
            return "2025-07-10".equals(dateStr) && ((Number) r[1]).intValue() == 2;
        });
        assertTrue(found, "En 2025-07-10 debe haber 2 tickets creados");
    }
}
