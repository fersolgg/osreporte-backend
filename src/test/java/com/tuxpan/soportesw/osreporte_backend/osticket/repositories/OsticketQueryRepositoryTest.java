package com.tuxpan.soportesw.osreporte_backend.osticket.repositories;

import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import org.junit.jupiter.api.Test;

import java.sql.Timestamp;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

public class OsticketQueryRepositoryTest {

    @Test
    public void getTicketsCreadosPorDia_usesNamedParametersAndBindsTimestamps() throws Exception {
        EntityManager em = mock(EntityManager.class);
        Query q = mock(Query.class);

        when(em.createNativeQuery(anyString())).thenReturn(q);
        when(q.setParameter(anyString(), any())).thenReturn(q);
        when(q.getResultList()).thenReturn(java.util.Collections.emptyList());

        OsticketQueryRepository repo = new OsticketQueryRepository();
        java.lang.reflect.Field f = OsticketQueryRepository.class.getDeclaredField("entityManager");
        f.setAccessible(true);
        f.set(repo, em);

        String from = "2025-10-01 00:00:00";
        String to = "2025-10-02 23:59:59";

        repo.getTicketsCreadosPorDia(from, to);

        verify(em).createNativeQuery(argThat(s -> s.contains(":from") && s.contains(":to")));
        verify(q).setParameter("from", Timestamp.valueOf(from));
        verify(q).setParameter("to", Timestamp.valueOf(to));
        verify(q).getResultList();
    }
}
