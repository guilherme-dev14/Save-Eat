package com.saveandeat.controller;

import com.saveandeat.dto.CadastroEmpresaRequest;
import com.saveandeat.dto.CadastroConsumidorRequest;
import com.saveandeat.service.AuthServiceCadastro;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@CrossOrigin(origins = "*")
public class AuthCadastroController {

    @Autowired
    private AuthServiceCadastro authServiceCadastro;

    @PostMapping("/cadastrar-empresa")
    public ResponseEntity<?> cadastrarEmpresa(@RequestBody CadastroEmpresaRequest req) {
        try {
            authServiceCadastro.cadastrarEmpresa(req);
            return ResponseEntity.ok("Empresa cadastrada com sucesso");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(409).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Erro ao cadastrar empresa");
        }
    }

    @PostMapping("/cadastrar-consumidor")
    public ResponseEntity<?> cadastrarConsumidor(@RequestBody CadastroConsumidorRequest req) {
        try {
            authServiceCadastro.cadastrarConsumidor(req);
            return ResponseEntity.ok("Consumidor cadastrado com sucesso");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(409).body(e.getMessage()); // 409 = CONFLICT
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Erro ao cadastrar consumidor");
        }
    }
}
