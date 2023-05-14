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
public class AdminClassDisplay {

    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    Integer class_id;

    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    Integer course_id;

    String course_name;

    String course_code;

    String department_name;

    String school_name;

    String semester_name;

    int capacity;

    int enrollment_num;

    String is_active;
}
