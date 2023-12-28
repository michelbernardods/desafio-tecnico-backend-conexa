package com.conexa.saude.service;

import static org.junit.jupiter.api.Assertions.*;

import com.conexa.saude.model.Agendamento;
import com.conexa.saude.model.Paciente;
import com.conexa.saude.repository.AgendamentoRepository;
import com.conexa.saude.repository.PacienteRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

class AgendamentoServiceTest {

    @Mock
    private AgendamentoRepository agendamentoRepository;

    @Mock
    private PacienteRepository pacienteRepository;

    @InjectMocks
    private AgendamentoService agendamentoService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCriarAgendamento_ValidData_ReturnsSuccessResponse() {

        LocalDateTime dataHora = LocalDateTime.now().plusDays(1);
        Map<String, Object> agendamentoData = new HashMap<>();
        agendamentoData.put("dataHora", dataHora.toString());

        Map<String, String> pacienteData = new HashMap<>();
        pacienteData.put("nome", "João Castro");
        pacienteData.put("cpf", "93701360022");
        agendamentoData.put("paciente", pacienteData);

        when(pacienteRepository.findByCpf(anyString())).thenReturn(null);
        when(pacienteRepository.save(any())).thenReturn(null);
        when(agendamentoRepository.save(any())).thenReturn(null);

        ResponseEntity<Object> response = agendamentoService.criarAgendamento("token", agendamentoData);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals("Agendamento criado com sucesso.", response.getBody());
        verify(pacienteRepository, times(1)).findByCpf(anyString());
        verify(pacienteRepository, times(1)).save(any());
        verify(agendamentoRepository, times(1)).save(any());
    }

    @Test
    void testCriarAgendamento_InvalidCPF_ReturnsErrorResponse() {

        LocalDateTime dataHora = LocalDateTime.now().plusDays(1);
        Map<String, Object> agendamentoData = new HashMap<>();
        agendamentoData.put("dataHora", dataHora.toString());

        Map<String, String> pacienteData = new HashMap<>();
        pacienteData.put("nome", "João Castro");
        pacienteData.put("cpf", "InvalidCPF"); // CPF inválido
        agendamentoData.put("paciente", pacienteData);

        ResponseEntity<Object> response = agendamentoService.criarAgendamento("token", agendamentoData);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("CPF inválido", response.getBody());
        verify(pacienteRepository, never()).findByCpf(anyString());
        verify(pacienteRepository, never()).save(any());
        verify(agendamentoRepository, never()).save(any());
    }
}
