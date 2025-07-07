package com.saveandeat.controller;

import com.saveandeat.model.Empresa;
import com.saveandeat.service.EmpresaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/empresa")
@CrossOrigin(origins = "*")
public class EmpresaController {

    @Autowired
    private EmpresaService empresaService;

    @GetMapping("/{email}")
    public ResponseEntity<?> getEmpresaByEmail(@PathVariable String email) {
        try {
            return empresaService.buscarPorEmail(email)
                    .map(ResponseEntity::ok)
                    .orElse(ResponseEntity.notFound().build());
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body("Erro ao buscar empresa: " + e.getMessage());
        }
    }

    @PutMapping("/{email}")
    public ResponseEntity<?> atualizarEmpresa(@PathVariable String email, @RequestBody Empresa atualizada) {
        try {
            Empresa empresa = empresaService.atualizar(email, atualizada);
            return ResponseEntity.ok(empresa);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body("Erro ao atualizar empresa: " + e.getMessage());
        }
    }

    @GetMapping("/id/{id}")
    public ResponseEntity<?> getEmpresaById(@PathVariable long id) {
        try {
            Empresa empresa = empresaService.buscarPorIdEmpresa(id);
            return ResponseEntity.ok(empresa);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body("Erro ao buscar empresa: " + e.getMessage());
        }
    }
}