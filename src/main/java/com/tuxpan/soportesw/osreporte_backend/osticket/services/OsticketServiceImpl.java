package com.tuxpan.soportesw.osreporte_backend.osticket.services;

import com.tuxpan.soportesw.osreporte_backend.osticket.dto.StaffPorEstadoDTO;
import com.tuxpan.soportesw.osreporte_backend.osticket.repositories.OsticketQueryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class OsticketServiceImpl implements OsticketService {

        @Autowired
        private OsticketQueryRepository osticketQueryRepository;

        @Override
        @Transactional(transactionManager = "osticketTransactionManager", readOnly = true)
        public List<StaffPorEstadoDTO> listadoStaffPorEstado(int estado) {
                List<Object[]> resultados = osticketQueryRepository.listadoStaffPorEstado(estado);
                return resultados.stream()
                                .map(obj -> new StaffPorEstadoDTO(
                                                obj[0] != null ? Long.valueOf(obj[0].toString()) : null,
                                                obj[1] != null ? obj[1].toString() : null,
                                                obj[2] != null ? obj[2].toString() : null,
                                                obj[3] != null ? obj[3].toString() : null,
                                                obj[4] != null ? obj[4].toString() : null))
                                .collect(Collectors.toList());
        }
}