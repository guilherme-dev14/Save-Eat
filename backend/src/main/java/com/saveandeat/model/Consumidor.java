package com.saveandeat.model;

public class Consumidor {
    private long id;
    private String nome;
    private String cpf;
    private String email;
    private String senha;
    private String bairro;
    private String categoriaFavorita;

    public Consumidor() {
    }

    public Consumidor(String nome, String cpf, String email, String senha) {
        this.nome = nome;
        this.cpf = cpf;
        this.email = email;
        this.senha = senha;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public String getBairro() {
        return bairro;
    }

    public void setBairro(String bairro) {
        this.bairro = bairro;
    }

    public String getCategoriaFavorita() {
        return categoriaFavorita;
    }

    public void setCategoriaFavorita(String categoriaFavorita) {
        this.categoriaFavorita = categoriaFavorita;
    }
}