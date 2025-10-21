# Especificación: fechas y estados para endpoints de reportes

Este documento describe el contrato mínimo para los parámetros de fecha y estado que usan los endpoints de reportes del backend. Está diseñado para ser claro y pequeño; el frontend debe cumplir el formato y los servicios backend deben validar y normalizar.

## Resumen
- Inputs aceptados:
  - `date_from` o `fechaInicio`: formato ISO `yyyy-MM-dd` (p. ej. `2025-10-01`).
  - `date_to` o `fechaFin`: formato ISO `yyyy-MM-dd` (p. ej. `2025-10-20`).
  - `estado`: string con uno de los valores permitidos (`TODOS`, `ABIERTO`, `PROGRESO`, `CERRADO`) — case‑insensitive.

## Timezone y normalización
- Zona obligatoria para normalización: `America/Santiago`.
- Normalización aplicada por el backend:
  - `startInstant` = `date_from` a las 00:00:00 en `America/Santiago` convertido a `Instant`.
  - `endInstant` = `date_to` + 1 día a las 00:00:00 en `America/Santiago` convertido a `Instant`, restando un pequeño epsilon si se desea un límite inclusivo (o usar `<= endInstantExclusive`). En práctica SQL, usar `BETWEEN :dateFrom AND :dateTo` después de convertir a `Timestamp`/`Instant` según el DB.

Ejemplo (Java):

```java
ZoneId zone = ZoneId.of("America/Santiago");
LocalDate from = LocalDate.parse(dateFromStr);
LocalDate to = LocalDate.parse(dateToStr);
Instant start = from.atStartOfDay(zone).toInstant();
Instant end = to.plusDays(1).atStartOfDay(zone).toInstant().minusNanos(1); // inclusivo
```

## Reglas de validación
- Formato: ambos `date_from` y `date_to` deben cumplir `yyyy-MM-dd`. Si no, devolver 400.
- Rango válido: `date_from` <= `date_to`. Si no, devolver 400.
- `maxRangeDays` (opcional): el servicio puede imponer un máximo (ej. 365 días). Si se supera, devolver 400 con mensaje claro.
- `estado`: debe mapear a uno de los valores del enum; usar `fromString` case-insensitive. Si inválido, devolver 400.

### Errores 4xx propuestos
Respuesta JSON (ejemplo):

```json
{
  "code": 400,
  "message": "Parámetros inválidos",
  "details": {
    "date_from": "formato inválido, use yyyy-MM-dd"
  }
}
```

## Mapeo a parámetros SQL (named params)
- El backend debe convertir `start` y `end` a `java.sql.Timestamp` o al tipo que acepte el driver y pasar parámetros nombrados a las consultas nativas o JPQL.

- Parámetros recomendados:
  - `:dateFrom` -> Timestamp.from(start)
  - `:dateTo` -> Timestamp.from(end)
  - `:estado` -> valor del enum (si aplica)

Ejemplo SQL (pseudo):

```sql
SELECT count(*) FROM ost_ticket
WHERE created_at BETWEEN :dateFrom AND :dateTo
  AND (:estado IS NULL OR status = :estado)
```

Nota: si `estado` equivale a `TODOS`, pasar `:estado = NULL` o implementar la condición `(:estado IS NULL OR status = :estado)` para omitir el filtro.

## Ejemplos de requests
- Request para todos los estados en octubre 2025:

```
GET /reportes/tickets?date_from=2025-10-01&date_to=2025-10-31&estado=TODOS
```

- Request para estado abierto entre 2025-10-01 y 2025-10-20:

```
GET /reportes/tickets?date_from=2025-10-01&date_to=2025-10-20&estado=ABIERTO
```

## Notas de implementación
- Siempre bindear parámetros y evitar concatenación de strings en consultas nativas.
- Preferir named params con `EntityManager#createNativeQuery` y `setParameter("dateFrom", Timestamp.from(start))`.
- Mantener los mensajes de error localizables en `messages.properties`.

---

Archivo generado automáticamente como especificación corta para la actividad de utilidades de fechas/estado.
