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
                return osticketQueryRepository.listadoStaffPorEstado(estado);
        }
}