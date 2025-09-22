// Implementación de la interfaz OsticketService
package com.tuxpan.soportesw.osreporte_backend.osticket.services;

// Importaciones de DTO, repositorio y utilidades de Spring
import com.tuxpan.soportesw.osreporte_backend.osticket.dto.StaffPorEstadoDTO;
import com.tuxpan.soportesw.osreporte_backend.osticket.repositories.OsticketQueryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.stream.Collectors;

// Indica que esta clase es un servicio gestionado por Spring
@Service
public class OsticketServiceImpl implements OsticketService {

        // Inyección del repositorio para consultas nativas
        @Autowired
        private OsticketQueryRepository osticketQueryRepository;

        // Implementación del método para obtener staff filtrado por estado
        @Override
        @Transactional(transactionManager = "osticketTransactionManager", readOnly = true)
        public List<StaffPorEstadoDTO> listadoStaffPorEstado(int estado) {
                // Ejecuta la consulta nativa y transforma los resultados en DTOs
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