package com.tuxpan.soportesw.osreporte_backend.utils;

import org.junit.jupiter.api.Test;
import java.time.ZoneId;
import static org.junit.jupiter.api.Assertions.*;

class DateRangeUtilTest {

    @Test
    void happyPathSingleDay() {
        DateRangeResult r = DateRangeUtil.parseAndValidate("2025-10-20", "2025-10-20", null);
        assertEquals("2025-10-20", r.getFrom().toString());
        assertEquals("2025-10-20", r.getTo().toString());
        assertEquals(ZoneId.of("America/Santiago"), r.getZone());
        assertTrue(r.getEndInstant().isAfter(r.getStartInstant()));
    }

    @Test
    void invalidFormatThrows() {
        assertThrows(IllegalArgumentException.class, () ->
            DateRangeUtil.parseAndValidate("2025/10/20", "2025-10-20", null)
        );
    }

    @Test
    void fromAfterToThrows() {
        assertThrows(IllegalArgumentException.class, () ->
            DateRangeUtil.parseAndValidate("2025-10-21", "2025-10-20", null)
        );
    }

    @Test
    void maxRangeExceededThrows() {
        assertThrows(IllegalArgumentException.class, () ->
            DateRangeUtil.parseAndValidate("2025-10-01", "2025-10-10", 5)
        );
    }
}
