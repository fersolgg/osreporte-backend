package com.tuxpan.soportesw.osreporte_backend.osticket.repositories;

import org.springframework.stereotype.Repository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import java.util.List;

@Repository
public class OsticketQueryRepository {
   
    @PersistenceContext(unitName = "osticket")
    private EntityManager entityManager;

    public List<Object[]> listadoStaffPorEstado(int estado) {
        String sql = "SELECT "
            + "staff_id, "
            + "username, "
            + "firstname, "
            + "lastname, "
            + "email "
            + "FROM ost_staff "
            + "WHERE isactive = " + estado;
        return entityManager.createNativeQuery(sql).getResultList();
    }

}
