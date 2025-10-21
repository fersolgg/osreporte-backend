package com.tuxpan.soportesw.osreporte_backend.osticket.repositories;

import org.springframework.stereotype.Repository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import java.util.ArrayList;
import java.util.List;

import com.tuxpan.soportesw.osreporte_backend.osticket.dto.StaffPorEstadoDTO;

@Repository
public class OsticketQueryRepository {
   
    @PersistenceContext(unitName = "osticket")
    private EntityManager entityManager;

    public List<StaffPorEstadoDTO> listadoStaffPorEstado(int estado) {
        String sql = "SELECT "
            + "staff_id, "
            + "username, "
            + "firstname, "
            + "lastname, "
            + "email "
            + "FROM ost_staff "
            + "WHERE isactive = :estado";

        Query q = entityManager.createNativeQuery(sql);
        q.setParameter("estado", estado);

        @SuppressWarnings("unchecked")
        List<Object[]> rows = q.getResultList();

        List<StaffPorEstadoDTO> result = new ArrayList<>();
        for (Object[] r : rows) {
            Long staffId = r[0] != null ? Long.valueOf(r[0].toString()) : null;
            String username = r[1] != null ? r[1].toString() : null;
            String firstname = r[2] != null ? r[2].toString() : null;
            String lastname = r[3] != null ? r[3].toString() : null;
            String email = r[4] != null ? r[4].toString() : null;
            result.add(new StaffPorEstadoDTO(staffId, username, firstname, lastname, email));
        }
        return result;
    }

}
