package com.conexa.saude.controller;

import com.conexa.saude.model.Medico;
import com.conexa.saude.service.MedicoService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/api/v1")
public class MedicoController {

    public final MedicoService service;

    public MedicoController(MedicoService service) {
        this.service = service;
    }

    @PostMapping("signup")
    public ResponseEntity<Object> cadastrarMedico(@RequestBody Medico medico) {
        return service.cadastar(medico);
    }


}
