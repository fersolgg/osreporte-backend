package com.tuxpan.soportesw.osreporte_backend.utils;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeParseException;
import java.time.temporal.ChronoUnit;

/**
 * Utilidad para parsear y validar parámetros date_from / date_to.
 */
public final class DateRangeUtil {
    public static final ZoneId SANTIAGO = ZoneId.of("America/Santiago");

    private DateRangeUtil() { }

    /**
     * Parsea y valida dos fechas en formato ISO (yyyy-MM-dd).
     * @param dateFrom fecha desde (yyyy-MM-dd), obligatoria
     * @param dateTo fecha hasta (yyyy-MM-dd), obligatoria
     * @param maxRangeDays null o número máximo de días permisibles (inclusive)
     * @return DateRangeResult con instantes start/end en UTC calculados desde la zona America/Santiago
     */
    public static DateRangeResult parseAndValidate(String dateFrom, String dateTo, Integer maxRangeDays) {
        if (dateFrom == null || dateFrom.isBlank()) {
            throw new IllegalArgumentException("date_from es requerido y debe tener formato yyyy-MM-dd");
        }
        if (dateTo == null || dateTo.isBlank()) {
            throw new IllegalArgumentException("date_to es requerido y debe tener formato yyyy-MM-dd");
        }
        LocalDate from;
        LocalDate to;
        try {
            from = LocalDate.parse(dateFrom);
            to = LocalDate.parse(dateTo);
        } catch (DateTimeParseException ex) {
            throw new IllegalArgumentException("Formato inválido: use yyyy-MM-dd", ex);
        }
        if (from.isAfter(to)) {
            throw new IllegalArgumentException("date_from debe ser anterior o igual a date_to");
        }
        long daysInclusive = ChronoUnit.DAYS.between(from, to) + 1;
        if (maxRangeDays != null && daysInclusive > maxRangeDays) {
            throw new IllegalArgumentException("El rango de fechas excede el máximo permitido de " + maxRangeDays + " días");
        }
        Instant start = from.atStartOfDay(SANTIAGO).toInstant();
        Instant end = to.plusDays(1).atStartOfDay(SANTIAGO).toInstant().minusNanos(1);
        return new DateRangeResult(from, to, start, end, SANTIAGO);
    }
}
