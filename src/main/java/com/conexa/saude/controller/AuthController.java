package com.conexa.saude.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashSet;
import java.util.Set;

@RestController
@RequestMapping("/api/v1")
public class AuthController {
    private final Set<String> blacklistedTokens = new HashSet<>();
    @PostMapping("logout")
    public ResponseEntity<String> logout(@RequestHeader("Authorization") String token) {

        blacklistedTokens.add(token);

        return ResponseEntity.ok("Token invalidado com sucesso");
    }
    @PostMapping("logoff")
    public ResponseEntity<String> logoff(@RequestHeader("Authorization") String token) {
        if (blacklistedTokens.contains(token)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Token inválido");
        }

        return ResponseEntity.ok("Logoff realizado com sucesso");
    }
}
