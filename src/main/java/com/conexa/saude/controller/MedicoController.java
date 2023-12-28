package com.conexa.saude.controller;

import com.conexa.saude.model.Agendamento;
import com.conexa.saude.model.Medico;
import com.conexa.saude.model.Paciente;
import com.conexa.saude.repository.PacienteRepository;
import com.conexa.saude.service.AgendamentoService;
import com.conexa.saude.service.MedicoService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.Map;

@Controller
@RequestMapping("/api/v1")
public class MedicoController {

    public final MedicoService medicoService;
    public final AgendamentoService agendamentoService;
    public final PacienteRepository pacienteRepository;


    public MedicoController(MedicoService service, AgendamentoService agendamentoService, PacienteRepository pacienteRepository) {
        this.medicoService = service;
        this.agendamentoService = agendamentoService;
        this.pacienteRepository = pacienteRepository;
    }

    @PostMapping("signup")
    public ResponseEntity<Object> cadastrarMedico(@RequestBody Medico medico) {
        return medicoService.cadastrar(medico);
    }
    @PostMapping("login")
    public ResponseEntity<Object> login(@RequestBody Map<String, String> loginData) {
        return medicoService.login(loginData);
    }
    @PostMapping("agendamento")
    public ResponseEntity<Object> criarAgendamento(@RequestHeader("Authorization") String token, @RequestBody Map<String, Object> agendamentoData) {
        return agendamentoService.criarAgendamento(token, agendamentoData);
    }
}
