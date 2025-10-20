package com.tuxpan.soportesw.osreporte_backend.utils;

/**
 * Enum para los estados canónicos del proyecto.
 */
public enum Estado {
    TODOS, ABIERTO, PROGRESO, CERRADO;

    /**
     * Convierte un string a Estado (case-insensitive).
     * null o empty -> TODOS
     */
    public static Estado fromString(String s) {
        if (s == null || s.isBlank()) return TODOS;
        try {
            return Estado.valueOf(s.trim().toUpperCase());
        } catch (IllegalArgumentException ex) {
            throw new IllegalArgumentException("Estado inválido: " + s + ". Valores válidos: TODOS, ABIERTO, PROGRESO, CERRADO");
        }
    }

    public boolean isFilterApplied() {
        return this != TODOS;
    }
}
