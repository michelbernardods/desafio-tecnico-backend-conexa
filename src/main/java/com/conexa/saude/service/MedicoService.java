package com.conexa.saude.service;

import com.conexa.saude.model.Medico;
import com.conexa.saude.repository.MedicoRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;


import static com.conexa.saude.config.validator.Validator.validarCPF;

@Service
public class MedicoService {
    private final MedicoRepository medicoRepository;
    private final PasswordEncoder encoder;


    public MedicoService(MedicoRepository medicoRepository, PasswordEncoder encoder) {
        this.medicoRepository = medicoRepository;
        this.encoder = encoder;
    }

    public ResponseEntity<Object> cadastar(Medico medico) {
        if (!validarCPF(medico.getCpf())) {
            return ResponseEntity.status(HttpStatus.OK).body("CPF invalido");
        }

        String resul = encoder.encode(medico.getSenha());
        System.out.println(resul);
        medico.setSenha(resul);

        medicoRepository.save(medico);
        return ResponseEntity.status(HttpStatus.OK).body("ok");
    }


}
