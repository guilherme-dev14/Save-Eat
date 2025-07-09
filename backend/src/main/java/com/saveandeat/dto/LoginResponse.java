package com.saveandeat.dto;

public class LoginResponse {
    private String token;
    private String tipoUsuario;
    private String email;
    private Long idEmpresa;

    public LoginResponse(String token, String tipoUsuario, String email, Long idEmpresa) {
        this.token = token;
        this.tipoUsuario = tipoUsuario;
        this.email = email;
        this.idEmpresa = idEmpresa;
    }

    public Long getIdEmpresa() {
        return idEmpresa;
    }

    public void setIdEmpresa(Long idEmpresa) {
        this.idEmpresa = idEmpresa;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getTipoUsuario() {
        return tipoUsuario;
    }

    public void setTipoUsuario(String tipoUsuario) {
        this.tipoUsuario = tipoUsuario;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}