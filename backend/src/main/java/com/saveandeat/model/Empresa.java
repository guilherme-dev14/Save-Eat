package com.saveandeat.model;

public class Empresa {
    private long id;
    private String nome;
    private String cnpj;
    private String email;
    private String senha;
    private Localizacao localizacao;

    public Empresa() {
    }

    public Empresa(String nome, String cnpj, String email, String senha,
            String logradouro, int numero, String bairro, String cidade,
            String cep, String estado) {
        this.nome = nome;
        this.cnpj = cnpj;
        this.email = email;
        this.senha = senha;
        this.localizacao = new Localizacao(logradouro, numero, bairro, cidade, cep, estado);
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

    public String getCnpj() {
        return cnpj;
    }

    public void setCnpj(String cnpj) {
        this.cnpj = cnpj;
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

    public Localizacao getLocalizacao() {
        return localizacao;
    }
    
    public void setLocalizacao(Localizacao localizacao) {
        this.localizacao = localizacao;
    }
}
