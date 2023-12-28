package com.conexa.saude.service;

import com.conexa.saude.model.Agendamento;
import com.conexa.saude.model.Paciente;
import com.conexa.saude.repository.AgendamentoRepository;
import com.conexa.saude.repository.PacienteRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import static com.conexa.saude.config.validator.Validator.validarCPF;

@Service
public class AgendamentoService {
    private final AgendamentoRepository agendamentoRepository;
    private final PacienteRepository pacienteRepository;
    Logger logger = LoggerFactory.getLogger(this.getClass());


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

            if (!validarCPF(cpfPaciente)) {
                logger.info("CPF inválido");
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("CPF inválido");
            }

            LocalDateTime agora = LocalDateTime.now();
            if (dataHora.isBefore(agora)) {
                logger.info("Não é possível agendar no passado");
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Não é possível agendar no passado.");
            }

            if (nomePaciente == null || cpfPaciente == null || nomePaciente.isEmpty() || cpfPaciente.isEmpty()) {
                logger.info("Dados de entrada inválidos");
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

            logger.info("Agendamento criado com sucesso");
            return ResponseEntity.status(HttpStatus.CREATED).body("Agendamento criado com sucesso.");
        } catch (Exception e) {
            logger.info("Erro ao criar agendamento. Dados de entrada inválidos");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Erro ao criar agendamento. Dados de entrada inválidos.");
        }
    }

}
