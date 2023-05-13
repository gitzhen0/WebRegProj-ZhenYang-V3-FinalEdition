package com.beaconfire.domain.jdbc;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class StudentClassDisplay {

    private int student_id;

    private int class_id;
    private String course_name;
    private String course_code;
    private String department_name;
    private String school_name;
    private String semester_name;
    private String status;

}

