package com.deployd.auth_service.emailpassword.dto;

import javax.validation.constraints.NotBlank;

public class changePasswordDTO {
    @NotBlank
    private String password;
    @NotBlank
    private String confirmarPassword;
    @NotBlank
    private String tokenPassword;

    public changePasswordDTO(){

    }

    public changePasswordDTO(String password, String confirmarPassword, String tokenPassword) {
        this.password = password;
        this.confirmarPassword = confirmarPassword;
        this.tokenPassword = tokenPassword;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getConfirmarPassword() {
        return confirmarPassword;
    }

    public void setConfirmarPassword(String confirmarPassword) {
        this.confirmarPassword = confirmarPassword;
    }

    public String getTokenPassword() {
        return tokenPassword;
    }

    public void setTokenPassword(String tokenPassword) {
        this.tokenPassword = tokenPassword;
    }
}
