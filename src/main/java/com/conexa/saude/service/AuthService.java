package com.conexa.saude.service;

import com.conexa.saude.repository.MedicoRepository;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.util.HashMap;
import java.util.Map;

@Service
public class AuthService {
    private final SecretKey secretKey = Keys.secretKeyFor(SignatureAlgorithm.HS256);


    public Map jwt(Map<String, String> loginData) {
        String email = loginData.get("email");

        String token = Jwts.builder()
                .setSubject(email)
                .signWith(secretKey)
                .compact();

        Map<String, String> response = new HashMap<>();
        response.put("token", token);

        return response;
    }
}
