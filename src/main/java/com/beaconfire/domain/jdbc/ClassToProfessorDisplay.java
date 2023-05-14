package com.beaconfire.domain.jdbc;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ClassToProfessorDisplay {

    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    Integer class_id;


    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    String first_name;

    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    String last_name;

    String full_name;

    String email;

    String department_name;

    String school_name;
}
