-- Fixtures comunes para tests de integración (H2 / test profile)
-- Este archivo contiene DDL idempotente para crear las tablas mínimas usadas
-- por los tests de reportes de osticket. No incluye datos de prueba concretos;
-- los tests pueden ejecutar INSERTs específicos después de ejecutar este script.
--
-- Uso sugerido desde tests Java: leer el recurso y ejecutar las sentencias o
-- ejecutar RUNSCRIPT desde H2 apuntando al archivo desplegado en disco.

-- Eliminar tablas si existen (idempotente)
DROP TABLE IF EXISTS ost_ticket;
DROP TABLE IF EXISTS ost_ticket_status;

-- Crear tabla de estados
CREATE TABLE ost_ticket_status (
  id INT PRIMARY KEY,
  name VARCHAR(100)
);

-- Crear tabla de tickets (campos mínimos usados en tests)
CREATE TABLE ost_ticket (
  id INT PRIMARY KEY,
  created TIMESTAMP,
  status_id INT,
  source VARCHAR(50)
);

-- Ejemplos de INSERT (descomentar y adaptar si quieres incluir datos por defecto)
-- INSERT INTO ost_ticket_status (id, name) VALUES (1, 'Open');
-- INSERT INTO ost_ticket (id, created, status_id, source) VALUES (1, '2025-06-10 09:00:00', 1, 'Web');
-- INSERT INTO ost_ticket (id, created, status_id, source) VALUES (2, '2025-07-05 10:00:00', 1, 'Email');

-- Nota: si prefieres que los tests reutilicen este archivo en vez de ejecutar DDL
-- en línea, en el siguiente paso puedo modificar cada test para cargar y ejecutar
-- este script al inicio de su método @Test (haré un cambio por turno).
