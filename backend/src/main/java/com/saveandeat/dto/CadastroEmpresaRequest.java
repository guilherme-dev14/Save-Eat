package com.saveandeat.dto;

import com.saveandeat.model.Localizacao;

public class CadastroEmpresaRequest {
    private String nome;
    private String cnpj;
    private String email;
    private String senha;
    private Localizacao localizacao;

    public CadastroEmpresaRequest() {}

    public CadastroEmpresaRequest(String nome, String cnpj, String email, String senha, Localizacao localizacao) {
        this.nome = nome;
        this.cnpj = cnpj;
        this.email = email;
        this.senha = senha;
        this.localizacao = localizacao;
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