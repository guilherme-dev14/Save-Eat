package com.saveandeat.controller;

import com.saveandeat.model.Consumidor;
import com.saveandeat.service.ConsumidorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/consumidores")
@CrossOrigin(origins = "*")
public class ConsumidorController {

    @Autowired
    private ConsumidorService consumidorService;

    @GetMapping("/{email}")
    public ResponseEntity<?> getConsumidorByEmail(@PathVariable String email) {
        try {
            return consumidorService.buscarPorEmail(email)
                    .map(ResponseEntity::ok)
                    .orElse(ResponseEntity.notFound().build());
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body("Erro ao buscar consumidor: " + e.getMessage());
        }
    }

    @PutMapping("/{email}")
    public ResponseEntity<?> atualizarConsumidor(@PathVariable String email, @RequestBody Consumidor atualizado) {
        try {
            Consumidor consumidor = consumidorService.atualizar(email, atualizado);
            return ResponseEntity.ok(consumidor);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body("Erro ao atualizar consumidor: " + e.getMessage());
        }
    }
}