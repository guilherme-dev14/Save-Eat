package com.saveandeat.service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.saveandeat.dto.LoginRequest;
import com.saveandeat.dto.LoginResponse;
import com.saveandeat.model.Empresa;
import com.saveandeat.model.Consumidor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.io.InputStream;
import java.util.List;

@Service
public class AuthService {

    private final ObjectMapper mapper = new ObjectMapper();

    @Autowired
    private PasswordEncoder passwordEncoder;

    public LoginResponse autenticar(LoginRequest request) throws Exception {

        InputStream empresaStream = getClass().getResourceAsStream("/data/empresas.json");
        List<Empresa> empresas = mapper.readValue(empresaStream, new TypeReference<List<Empresa>>() {
        });

        for (Empresa e : empresas) {
            if (e.getEmail().equalsIgnoreCase(request.getEmail())
                    && passwordEncoder.matches(request.getSenha(), e.getSenha())) {
                return new LoginResponse("fake-jwt-token", "empresa", e.getEmail(), e.getId());
            }
        }

        InputStream consumidorStream = getClass().getResourceAsStream("/data/consumidores.json");
        List<Consumidor> consumidores = mapper.readValue(consumidorStream, new TypeReference<List<Consumidor>>() {
        });

        for (Consumidor c : consumidores) {
            if (c.getEmail().equalsIgnoreCase(request.getEmail())
                    && passwordEncoder.matches(request.getSenha(), c.getSenha())) {
                return new LoginResponse("fake-jwt-token", "consumidor", c.getEmail(), null);

            }
        }

        return null;
    }
}
