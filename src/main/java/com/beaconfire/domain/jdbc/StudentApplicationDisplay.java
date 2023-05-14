package com.beaconfire.domain.jdbc;

import com.fasterxml.jackson.annotation.JsonInclude;
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

    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    Integer application_id;

    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    Integer student_id;

    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    Integer class_id;

    String course_name;

    String course_code;

    String semester_name;

    LocalTime creation_time;

    String request;

    String status;

    String feedback;
}
