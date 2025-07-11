package com.saveandeat.controller;

import com.saveandeat.model.Produto;
import com.saveandeat.service.ProdutoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/produtos")
@CrossOrigin(origins = "*")
public class ProdutoController {

    @Autowired
    private ProdutoService service;

    @GetMapping("/todos")
    public List<Produto> listarTodos() throws Exception {
        return service.listarTodos();
    }

    @GetMapping("/destaques")
    public List<Produto> listarDestaques() throws Exception {
        return service.listarDestaques();
    }

    @GetMapping("/ForaValidade")
    public List<Produto> listarForaValidade() throws Exception {
        return service.listarForaValidade();
    }

    @GetMapping("/Empresa/Vencidos")
    public List<Produto> listarVencidosEmpresa(@RequestParam Long idEmpresa) throws Exception {
        return service.listarVencidosEmpresa(idEmpresa);
    }

    @GetMapping("/Empresa/Todos")
    public List<Produto> listarTodosEmpresa(@RequestParam Long idEmpresa) throws Exception {
        return service.listarTodosEmpresa(idEmpresa);
    }

    @GetMapping("/ProximoValidade")
    public List<Produto> listarProximoValidade() throws Exception {
        return service.listarPertoDaValidade();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Produto> buscarPorId(@PathVariable Long id) throws Exception {
        return service.buscarPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public Produto adicionar(@RequestBody Produto produto) throws Exception {
        return service.adicionar(produto);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Produto> atualizar(@PathVariable Long id, @RequestBody Produto produto) throws Exception {
        if (service.buscarPorId(id).isPresent()) {
            return ResponseEntity.ok(service.atualizar(id, produto));
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> remover(@PathVariable Long id) throws Exception {
        if (service.buscarPorId(id).isPresent()) {
            service.remover(id);
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
