
package com.tuxpan.soportesw.osreporte_backend.controller;

import com.tuxpan.soportesw.osreporte_backend.osticket.dto.StaffPorEstadoDTO;
import com.tuxpan.soportesw.osreporte_backend.osticket.services.OsticketService;
import com.tuxpan.soportesw.osreporte_backend.utils.MessageUtil;
import com.tuxpan.soportesw.osreporte_backend.utils.ApiResponse;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;

@RestController
@RequestMapping("/osticket")
public class OsticketController {

    private final OsticketService service;
    private final MessageUtil messageUtil;

    public OsticketController(OsticketService service, MessageUtil messageUtil) {
        this.service = service;
        this.messageUtil = messageUtil;
    }

    @GetMapping("/listado-staff-por-estado")
    public ApiResponse<List<StaffPorEstadoDTO>> listadoStaffPorEstado(@RequestParam int estado) {
        List<StaffPorEstadoDTO> data = service.listadoStaffPorEstado(estado);
        String msg = messageUtil.get("info.success");
        return new ApiResponse<>(data, msg, null);
    }
}