package com.saveandeat.service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.saveandeat.model.Empresa;
import org.springframework.stereotype.Service;

import java.io.File;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class EmpresaService {

    private final ObjectMapper mapper;
    private final String basePath = "src/main/resources/data";
    private final String fileName = "empresas.json";

    public EmpresaService() {
        this.mapper = new ObjectMapper()
                .registerModule(new JavaTimeModule())
                .disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
    }

    private File getFile() {
        return Paths.get(basePath, fileName).toFile();
    }

    private List<Empresa> readEmpresas() throws Exception {
        File file = getFile();
        if (!file.exists())
            return new ArrayList<>();
        return mapper.readValue(file, new TypeReference<List<Empresa>>() {
        });
    }

    private void saveEmpresas(List<Empresa> empresas) throws Exception {
        mapper.writerWithDefaultPrettyPrinter().writeValue(getFile(), empresas);
    }

    public Optional<Empresa> buscarPorEmail(String email) throws Exception {
        return readEmpresas().stream()
                .filter(e -> e.getEmail().equalsIgnoreCase(email))
                .findFirst();
    }

    public Empresa buscarPorIdEmpresa(long id) throws Exception {
        return readEmpresas().stream()
                .filter(e -> e.getId() == id)
                .findFirst()
                .orElseThrow(() -> new Exception("Empresa não encontrada"));
    }

    public Empresa atualizar(String email, Empresa atualizada) throws Exception {
        List<Empresa> empresas = readEmpresas();
        empresas = empresas.stream().map(e -> {
            if (e.getEmail().equalsIgnoreCase(email)) {
                atualizada.setEmail(email);
                return atualizada;
            }
            return e;
        }).toList();
        saveEmpresas(empresas);
        return atualizada;
    }
}