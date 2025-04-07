package com.deployd.auth_service.dto;


import javax.validation.constraints.NotBlank;

public class LoginUsuario {
    @NotBlank
    private String nombreUsuario;
    @NotBlank
    private String password;


    public @NotBlank String getNombreUsuario() {
        return nombreUsuario;
    }

    public void setNombreUsuario(@NotBlank String nombreUsuario) {
        this.nombreUsuario = nombreUsuario;
    }

    public @NotBlank String getPassword() {
        return password;
    }

    public void setPassword(@NotBlank String password) {
        this.password = password;
    }
}
