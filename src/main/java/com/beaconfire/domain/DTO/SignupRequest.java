package com.beaconfire.domain.DTO;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SignupRequest {

    @Email(message = "Please provide a valid email address")
    @NotBlank(message = "Email field cannot be empty")
    private String email;

    @NotBlank(message = "Password field cannot be empty")
    private String password;

    @NotBlank(message = "first_name field cannot be empty")
    private String first_name;

    @NotBlank(message = "last_name field cannot be empty")
    private String last_name;

    @NotNull(message = "Department ID cannot be null")
    @Positive(message = "Department ID must be a positive number")
    private Integer department_id;
}
