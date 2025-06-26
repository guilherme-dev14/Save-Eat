package com.saveandeat.service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.saveandeat.dto.CadastroEmpresaRequest;
import com.saveandeat.dto.CadastroConsumidorRequest;
import com.saveandeat.model.Empresa;
import com.saveandeat.model.Consumidor;
import com.saveandeat.model.Localizacao;
import org.springframework.stereotype.Service;

import java.io.File;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

@Service
public class AuthServiceCadastro {

    private final ObjectMapper mapper = new ObjectMapper();
    private final String basePath = "src/main/resources/data";

    public void cadastrarEmpresa(CadastroEmpresaRequest req) throws Exception {
        File file = Paths.get(basePath, "empresas.json").toFile();
        List<Empresa> empresas = file.exists() ?
            mapper.readValue(file, new TypeReference<List<Empresa>>() {}) : new ArrayList<>();

        long novoId = empresas.stream().mapToLong(Empresa::getId).max().orElse(0L) + 1;

        Empresa nova = new Empresa();
        nova.setId(novoId);
        nova.setNome(req.getNome());
        nova.setCnpj(req.getCnpj());
        nova.setEmail(req.getEmail());
        nova.setSenha(req.getSenha());

        Localizacao loc = req.getLocalizacao();
        if (loc != null) {
            loc.setId(System.currentTimeMillis());
            nova.setLocalizacao(loc);
        }

        empresas.add(nova);
        mapper.writerWithDefaultPrettyPrinter().writeValue(file, empresas);
    }

    public void cadastrarConsumidor(CadastroConsumidorRequest req) throws Exception {
        File file = Paths.get(basePath, "consumidores.json").toFile();
        List<Consumidor> consumidores = file.exists() ?
            mapper.readValue(file, new TypeReference<List<Consumidor>>() {}) : new ArrayList<>();

        long novoId = consumidores.stream().mapToLong(Consumidor::getId).max().orElse(0) + 1;

        Consumidor novo = new Consumidor();
        novo.setId(novoId);
        novo.setNome(req.getNome());
        novo.setCpf(req.getCpf());
        novo.setEmail(req.getEmail());
        novo.setSenha(req.getSenha());

        consumidores.add(novo);
        mapper.writerWithDefaultPrettyPrinter().writeValue(file, consumidores);
    }
}