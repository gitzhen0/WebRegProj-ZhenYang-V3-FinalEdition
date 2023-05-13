package com.beaconfire.domain.jdbc;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalTime;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class StudentApplicationDisplay {
    int application_id;

    int student_id;

    int class_id;

    String course_name;

    String course_code;

    String semester_name;

    LocalTime creation_time;

    String request;

    String status;

    String feedback;
}
