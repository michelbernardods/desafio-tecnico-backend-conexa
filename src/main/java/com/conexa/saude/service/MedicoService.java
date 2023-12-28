package com.conexa.saude.service;

import com.conexa.saude.model.Medico;
import com.conexa.saude.repository.MedicoRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;


import java.util.Map;
import java.util.Objects;

import static com.conexa.saude.config.validator.Validator.validarCPF;

@Service
public class MedicoService {
    private final MedicoRepository medicoRepository;
    private final AuthService authService;
    private final PasswordEncoder encoder;

    Logger logger = LoggerFactory.getLogger(this.getClass());



    public MedicoService(MedicoRepository medicoRepository, AuthService authService, PasswordEncoder encoder) {
        this.medicoRepository = medicoRepository;
        this.authService = authService;
        this.encoder = encoder;
    }

    public ResponseEntity<Object> cadastrar(Medico medico) {
        Medico medicoExistente = medicoRepository.findByCpf(medico.getCpf());

        if (medicoExistente != null && medicoExistente.getCpf() != null) {
            logger.info("Médico com esse CPF já está cadastrado");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Médico com esse CPF já está cadastrado");
        }

        if (!validarCPF(medico.getCpf())) {
            logger.info("CPF inválido");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("CPF inválido");
        }

        if (Objects.equals(medico.getSenha(), "")) {
            logger.info("Senha não pode estar vazia");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Senha não pode estar vazia.");
        }

        if (!Objects.equals(medico.getSenha(), medico.getConfirmacaoSenha())) {
            logger.info("Senha de confirmação não está igual");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Senha de confirmação não está igual");
        }

        String passwordEncode = encoder.encode(medico.getSenha());
        medico.setSenha(passwordEncode);

        medicoRepository.save(medico);
        logger.info("Cadastro realizado com sucesso");
        return ResponseEntity.status(HttpStatus.OK).body("Cadastro realizado com sucesso");
    }

    public ResponseEntity<Object> login(Map<String, String> loginData) {
        return ResponseEntity.ok(authService.jwt(loginData));

    }
}
