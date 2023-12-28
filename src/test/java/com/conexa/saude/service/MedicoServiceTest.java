package com.conexa.saude.service;

import com.conexa.saude.model.Medico;
import com.conexa.saude.repository.MedicoRepository;
import com.conexa.saude.service.AuthService;
import com.conexa.saude.service.MedicoService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class MedicoServiceTest {

    @Mock
    private MedicoRepository medicoRepository;

    @Mock
    private AuthService authService;

    @Mock
    private PasswordEncoder encoder;

    @InjectMocks
    private MedicoService medicoService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
    }


    @Test
    void testCadastrar_ValidMedico_ReturnsSuccessResponse() {

        Medico medico = new Medico();
        medico.setCpf("93701360022");
        medico.setSenha("password");
        medico.setConfirmacaoSenha("password");

        when(medicoRepository.findByCpf(anyString())).thenReturn(null);
        when(encoder.encode(anyString())).thenReturn("encodedPassword");
        when(medicoRepository.save(any())).thenReturn(medico); // Aqui estamos ajustando o retorno do método save()

        ResponseEntity<Object> response = medicoService.cadastrar(medico);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Cadastro realizado com sucesso", response.getBody());
        verify(medicoRepository, times(1)).findByCpf(anyString());
        verify(medicoRepository, times(1)).save(any());
    }


    @Test
    void testCadastrar_MedicoWithExistingCPF_ReturnsErrorResponse() {

        String existingCpf = "12345678901";
        Medico existingMedico = new Medico();
        existingMedico.setCpf(existingCpf);

        Medico medico = new Medico();
        medico.setCpf(existingCpf);

        when(medicoRepository.findByCpf(existingCpf)).thenReturn(existingMedico);

        ResponseEntity<Object> response = medicoService.cadastrar(medico);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("Médico com esse CPF já está cadastrado", response.getBody());
        verify(medicoRepository, times(1)).findByCpf(existingCpf);
        verify(medicoRepository, never()).save(any());
    }

    @Test
    void testCadastrar_InvalidCPF_ReturnsErrorResponse() {

        Medico medico = new Medico();
        medico.setCpf("123456");

        ResponseEntity<Object> response = medicoService.cadastrar(medico);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("CPF inválido", response.getBody());
        verify(medicoRepository, never()).save(any());
    }


    @Test
    void testCadastrar_EmptyPassword_ReturnsErrorResponse() {

        Medico medico = new Medico();
        medico.setCpf("93701360022");
        medico.setSenha("");
        medico.setConfirmacaoSenha("");

        ResponseEntity<Object> response = medicoService.cadastrar(medico);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("Senha não pode estar vazia.", response.getBody());
        verify(medicoRepository, never()).save(any());
    }


    @Test
    void testCadastrar_PasswordMismatch_ReturnsErrorResponse() {

        Medico medico = new Medico();
        medico.setCpf("93701360022");
        medico.setSenha("password1");
        medico.setConfirmacaoSenha("password2");

        ResponseEntity<Object> response = medicoService.cadastrar(medico);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("Senha de confirmação não está igual", response.getBody());
        verify(medicoRepository, never()).save(any());
    }


    @Test
    void testLogin_ValidLoginData_ReturnsToken() {

        Map<String, String> loginData = new HashMap<>();
        loginData.put("email", "test@example.com");

        when(authService.jwt(any())).thenReturn(new HashMap<>());

        ResponseEntity<Object> response = medicoService.login(loginData);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        verify(authService, times(1)).jwt(any());
    }
}
