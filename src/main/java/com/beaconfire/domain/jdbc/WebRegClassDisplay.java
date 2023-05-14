package com.beaconfire.domain.jdbc;


import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class WebRegClassDisplay {

    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    String class_id;
    String course_name;

    String course_code;

    String department_name;

    String school_name;

    String course_description;

    Integer capacity;

    Integer enrollment_num;

    String is_active;
}
