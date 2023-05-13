package com.beaconfire.domain.jdbc;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ClassToProfessorDisplay {

    int class_id;

    String first_name;

    String last_name;

    String email;

    String department_name;

    String school_name;
}
