package com.beaconfire.domain.jdbc;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class WebRegClassDisplay {

    String class_id;
    String course_name;

    String course_code;

    String department_name;

    String school_name;

    String course_description;

    int capacity;

    int enrollment_num;

    String is_active;
}
