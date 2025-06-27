package com.saveandeat.service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.saveandeat.model.Consumidor;
import org.springframework.stereotype.Service;

import java.io.File;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ConsumidorService {

    private final ObjectMapper mapper;
    private final String basePath = "src/main/resources/data";
    private final String fileName = "consumidores.json";

    public ConsumidorService() {
        this.mapper = new ObjectMapper()
                .registerModule(new JavaTimeModule())
                .disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
    }

    private File getFile() {
        return Paths.get(basePath, fileName).toFile();
    }

    private List<Consumidor> readConsumidores() throws Exception {
        File file = getFile();
        if (!file.exists()) return new ArrayList<>();
        return mapper.readValue(file, new TypeReference<List<Consumidor>>() {});
    }

    private void saveConsumidores(List<Consumidor> consumidores) throws Exception {
        mapper.writerWithDefaultPrettyPrinter().writeValue(getFile(), consumidores);
    }

    public Optional<Consumidor> buscarPorEmail(String email) throws Exception {
        return readConsumidores().stream()
                .filter(c -> c.getEmail().equalsIgnoreCase(email))
                .findFirst();
    }

    public Consumidor atualizar(String email, Consumidor atualizada) throws Exception {
        List<Consumidor> consumidores = readConsumidores();
        consumidores = consumidores.stream().map(c -> {
            if (c.getEmail().equalsIgnoreCase(email)) {
                atualizada.setId(c.getId());
                atualizada.setSenha(c.getSenha()); // preserva senha
                return atualizada;
            }
            return c;
        }).toList();
        saveConsumidores(consumidores);
        return atualizada;
    }
}