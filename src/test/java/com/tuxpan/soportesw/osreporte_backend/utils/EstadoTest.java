package com.tuxpan.soportesw.osreporte_backend.utils;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class EstadoTest {

    @Test
    void fromString_caseInsensitive() {
        assertEquals(Estado.ABIERTO, Estado.fromString("abierto"));
        assertEquals(Estado.ABIERTO, Estado.fromString("ABIERTO"));
        assertEquals(Estado.PROGRESO, Estado.fromString("Progreso"));
        assertEquals(Estado.TODOS, Estado.fromString("todos"));
    }

    @Test
    void fromString_invalid_throws() {
        assertThrows(IllegalArgumentException.class, () -> Estado.fromString("INVALID_ESTADO"));
    }

}
