package com.ecovidasas.dto;

/*
 DTO que devuelve el resultado
 del proceso de autenticación.
*/
public class LoginResponse {

    private String mensaje;
    private Boolean autenticado;
    private String token;

    public LoginResponse() {
    }

    public LoginResponse(String mensaje, Boolean autenticado, String token) {
        this.mensaje = mensaje;
        this.autenticado = autenticado;
        this.token = token;
    }

    public String getMensaje() {
        return mensaje;
    }

    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }

    public Boolean getAutenticado() {
        return autenticado;
    }

    public void setAutenticado(Boolean autenticado) {
        this.autenticado = autenticado;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

}