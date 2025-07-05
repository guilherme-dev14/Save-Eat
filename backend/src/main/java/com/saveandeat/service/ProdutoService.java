package com.saveandeat.service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.saveandeat.model.Produto;
import org.springframework.stereotype.Service;
import com.saveandeat.model.Empresa;
import java.io.File;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ProdutoService {

    private final ObjectMapper mapper;
    private final String basePath = "src/main/resources/data";
    private final String fileName = "produtos.json";

    public ProdutoService() {
        this.mapper = new ObjectMapper()
                .registerModule(new JavaTimeModule())
                .disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
    }

    private File getFile() {
        return Paths.get(basePath, fileName).toFile();
    }

    private List<Produto> readProdutos() throws Exception {
        File file = getFile();
        if (!file.exists()) return new ArrayList<>();
        return mapper.readValue(file, new TypeReference<List<Produto>>() {});
    }

    private void saveProdutos(List<Produto> produtos) throws Exception {
        mapper.writerWithDefaultPrettyPrinter().writeValue(getFile(), produtos);
    }

    public List<Produto> listarTodos() throws Exception {
        return readProdutos();
    }

    public Optional<Produto> buscarPorId(Long id) throws Exception {
        return readProdutos().stream()
                .filter(p -> p.getId().equals(id))
                .findFirst();
    }

    public List<Produto> listarDestaques() throws Exception {
        return readProdutos().stream()
                .filter(p -> p.getDataValidade().isBefore(LocalDate.now().plusDays(7)))
                .collect(Collectors.toList());
    }

    public Produto adicionar(Produto produto) throws Exception {
        List<Produto> produtos = readProdutos();
        Long novoId = produtos.stream().mapToLong(Produto::getId).max().orElse(0L) + 1;
        produto.setId(novoId);
        produtos.add(produto);
        saveProdutos(produtos);
        return produto;
    }

    public Produto atualizar(Long id, Produto produtoAtualizado) throws Exception {
        List<Produto> produtos = readProdutos();
        produtos = produtos.stream().map(p -> {
            if (p.getId().equals(id)) {
                produtoAtualizado.setId(id);
                return produtoAtualizado;
            }
            return p;
        }).collect(Collectors.toList());
        saveProdutos(produtos);
        return produtoAtualizado;
    }

    public void remover(Long id) throws Exception {
        List<Produto> produtos = readProdutos();
        produtos = produtos.stream()
                .filter(p -> !p.getId().equals(id))
                .collect(Collectors.toList());
        saveProdutos(produtos);
    }
}
