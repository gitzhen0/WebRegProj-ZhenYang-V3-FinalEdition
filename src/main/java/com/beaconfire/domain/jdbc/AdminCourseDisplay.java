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
public class AdminCourseDisplay {

    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    Integer course_id;

    String course_name;

    String course_code;

    String department_name;

    String school_name;

    String description;
}
