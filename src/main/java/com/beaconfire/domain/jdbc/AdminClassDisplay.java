package com.beaconfire.domain.jdbc;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class AdminClassDisplay {

    int class_id;

    int course_id;

    String course_name;

    String course_code;

    String department_name;

    String school_name;

    String semester_name;

    int capacity;

    int enrollment_num;

    String is_active;
}
