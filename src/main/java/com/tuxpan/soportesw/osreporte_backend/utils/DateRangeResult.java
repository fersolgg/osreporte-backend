package com.tuxpan.soportesw.osreporte_backend.utils;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;

/**
 * Resultado inmutable del parseo/normalización de un rango de fechas.
 */
public final class DateRangeResult {
    private final LocalDate from;
    private final LocalDate to;
    private final Instant startInstant;
    private final Instant endInstant;
    private final ZoneId zone;

    public DateRangeResult(LocalDate from, LocalDate to, Instant startInstant, Instant endInstant, ZoneId zone) {
        this.from = from;
        this.to = to;
        this.startInstant = startInstant;
        this.endInstant = endInstant;
        this.zone = zone;
    }

    public LocalDate getFrom() {
        return from;
    }

    public LocalDate getTo() {
        return to;
    }

    public Instant getStartInstant() {
        return startInstant;
    }

    public Instant getEndInstant() {
        return endInstant;
    }

    public ZoneId getZone() {
        return zone;
    }
}
