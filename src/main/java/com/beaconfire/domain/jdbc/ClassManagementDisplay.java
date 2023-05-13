package com.beaconfire.domain.jdbc;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ClassManagementDisplay {
    int class_id;

    String course_name;

    String course_code;

    String department_name;

    String school_name;

    String enrollment_num;

    String capacity;

    String semester_name;

    String isEnrolled;
}
