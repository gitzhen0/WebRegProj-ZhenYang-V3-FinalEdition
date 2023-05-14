package com.beaconfire.domain.DTO;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

public class AuthenticationRequest {

    @Email(message = "Please provide a valid email address")
    @NotBlank(message = "Email field cannot be empty")
    private String email;

    @NotBlank(message = "Password field cannot be empty")
    private String password;

    // Getters and setters

    public String getEmail() {
        return this.email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return this.password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}

