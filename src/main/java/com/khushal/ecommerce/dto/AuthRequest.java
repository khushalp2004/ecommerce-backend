package com.khushal.ecommerce.dto;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

public class AuthRequest {

    @NotBlank
    @Email
    private String email;

    @NotBlank
    private String password;

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
