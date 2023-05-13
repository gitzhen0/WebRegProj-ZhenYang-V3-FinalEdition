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
public class AdminApplication {

    String first_name;

    String last_name;

    String email;

    String course_name;

    int class_id;

    String semester_name;

    String request;

    LocalTime creation_time;

    String status;

    int student_id;

    int application_id;

}
