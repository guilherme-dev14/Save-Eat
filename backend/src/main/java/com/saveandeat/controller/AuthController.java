package com.saveandeat.controller;

import com.saveandeat.dto.LoginRequest;
import com.saveandeat.dto.LoginResponse;
import com.saveandeat.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@CrossOrigin(origins = "*")
public class AuthController {

    @Autowired
    private AuthService authService;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest request) {
        try {
            LoginResponse response = authService.autenticar(request);
            if (response != null) {
                return ResponseEntity.ok(response);
            }
            return ResponseEntity.status(401).body("Credenciais inválidas");
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Erro interno ao autenticar");
        }
    }
}
