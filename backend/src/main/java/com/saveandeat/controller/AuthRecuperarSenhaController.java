package com.saveandeat.controller;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.saveandeat.model.Consumidor;
import com.saveandeat.model.Empresa;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.io.File;
import java.util.*;

@RestController
@RequestMapping("/auth")
@CrossOrigin(origins = "*")
public class AuthRecuperarSenhaController {

    private final ObjectMapper objectMapper = new ObjectMapper();
    private final String basePath = "src/main/resources/data/";
    private final Map<String, String> tokens = new HashMap<>();

    @Autowired
    private PasswordEncoder passwordEncoder;

    @PostMapping("/recuperar-senha")
    public ResponseEntity<?> recuperarSenha(@RequestBody Map<String, String> body) {
        String email = body.get("email");

        if (email == null || email.isBlank()) {
            return ResponseEntity.badRequest().body(Map.of("mensagem", "E-mail inválido."));
        }

        if (!buscarEmail(email)) {
            return ResponseEntity.ok(Map.of("mensagem", "E-mail não encontrado!"));
        }

        String token = UUID.randomUUID().toString();
        tokens.put(token, email);

        // Simula envio de e-mail
        String link = "http://localhost:8081/redefinir-senha?token=" + token;
        System.out.println("Simulador de e-mail: redefinição enviada para " + email);
        System.out.println("Link de redefinição: " + link);

        return ResponseEntity.ok(Map.of("mensagem", "Se o e-mail estiver cadastrado, enviaremos uma mensagem."));
    }

    @PostMapping("/redefinir-senha")
    public ResponseEntity<?> redefinirSenha(@RequestBody Map<String, String> body) {
        String token = body.get("token");
        String novaSenha = body.get("novaSenha");

        if (token == null || novaSenha == null) {
            return ResponseEntity.badRequest().body(Map.of("mensagem", "Dados inválidos."));
        }

        String email = tokens.get(token);
        if (email == null) {
            return ResponseEntity.badRequest().body(Map.of("mensagem", "Token inválido ou expirado."));
        }

        boolean sucesso = atualizarSenha(email, novaSenha);
        tokens.remove(token);

        if (!sucesso) {
            return ResponseEntity.internalServerError().body(Map.of("mensagem", "Erro ao atualizar senha."));
        }

        return ResponseEntity.ok(Map.of("mensagem", "Senha redefinida com sucesso."));
    }

    private boolean buscarEmail(String email) {
        try {
            File consumidorFile = new File(basePath + "consumidores.json");
            List<Consumidor> consumidores = objectMapper.readValue(consumidorFile, new TypeReference<>() {});
            for (Consumidor c : consumidores) {
                if (c.getEmail().equalsIgnoreCase(email)) return true;
            }

            File empresaFile = new File(basePath + "empresas.json");
            List<Empresa> empresas = objectMapper.readValue(empresaFile, new TypeReference<>() {});
            for (Empresa e : empresas) {
                if (e.getEmail().equalsIgnoreCase(email)) return true;
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    private boolean atualizarSenha(String email, String novaSenha) {
        try {
            File consumidorFile = new File(basePath + "consumidores.json");
            List<Consumidor> consumidores = objectMapper.readValue(consumidorFile, new TypeReference<>() {});
            boolean atualizado = false;
            novaSenha = passwordEncoder.encode(novaSenha);

            for (Consumidor c : consumidores) {
                if (c.getEmail().equalsIgnoreCase(email)) {
                    c.setSenha(novaSenha);
                    atualizado = true;
                    break;
                }
            }

            if (atualizado) {
                objectMapper.writeValue(consumidorFile, consumidores);
                return true;
            }

            File empresaFile = new File(basePath + "empresas.json");
            List<Empresa> empresas = objectMapper.readValue(empresaFile, new TypeReference<>() {});
            for (Empresa e : empresas) {
                if (e.getEmail().equalsIgnoreCase(email)) {
                    e.setSenha(novaSenha);
                    objectMapper.writeValue(empresaFile, empresas);
                    return true;
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
}
