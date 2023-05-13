package com.beaconfire.domain.DTO;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SignupRequest {

    private String email;
    private String password;
    private String first_name;

    private String last_name;

    private Integer department_id;
}
