package com.conexa.saude.service;

import com.conexa.saude.model.Agendamento;
import com.conexa.saude.model.Paciente;
import com.conexa.saude.repository.AgendamentoRepository;
import com.conexa.saude.repository.PacienteRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

@Service
public class AgendamentoService {
    private final AgendamentoRepository agendamentoRepository;
    private final PacienteRepository pacienteRepository;

    public AgendamentoService(AgendamentoRepository agendamentoRepository, PacienteRepository pacienteRepository) {
        this.agendamentoRepository = agendamentoRepository;
        this.pacienteRepository = pacienteRepository;
    }

    public ResponseEntity<Object> criarAgendamento(String token, Map<String, Object> agendamentoData) {
        try {
            LocalDateTime dataHora = LocalDateTime.parse((String) agendamentoData.get("dataHora"));
            Map<String, String> pacienteData = (Map<String, String>) agendamentoData.get("paciente");

            String nomePaciente = pacienteData.get("nome");
            String cpfPaciente = pacienteData.get("cpf");

            LocalDateTime agora = LocalDateTime.now();
            if (dataHora.isBefore(agora)) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Não é possível agendar no passado.");
            }

            if (nomePaciente == null || cpfPaciente == null || nomePaciente.isEmpty() || cpfPaciente.isEmpty()) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Dados de entrada inválidos.");
            }

            Paciente paciente = pacienteRepository.findByCpf(cpfPaciente);
            if (paciente == null) {
                paciente = new Paciente();
                paciente.setNome(nomePaciente);
                paciente.setCpf(cpfPaciente);
                pacienteRepository.save(paciente);
            }

            Agendamento agendamento = new Agendamento();
            agendamento.setDataHora(dataHora);
            agendamento.setPaciente(paciente);
            agendamentoRepository.save(agendamento);

            return ResponseEntity.status(HttpStatus.CREATED).body("Agendamento criado com sucesso.");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Erro ao criar agendamento. Dados de entrada inválidos.");
        }
    }

}
