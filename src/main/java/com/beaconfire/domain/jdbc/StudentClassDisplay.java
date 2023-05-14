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
public class StudentClassDisplay {

    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private Integer student_id;

    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private Integer class_id;
    private String course_name;
    private String course_code;
    private String department_name;
    private String school_name;
    private String semester_name;
    private String status;

}

