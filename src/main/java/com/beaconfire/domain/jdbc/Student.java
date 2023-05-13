package com.beaconfire.domain.jdbc;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Student {
    int id;

    String first_name;

    String last_name;

    String email;

    String password;

    int department_id;

    int is_active;
    int is_admin;
}
