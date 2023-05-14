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
public class AdminApplication {

    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    String first_name;

    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    String last_name;

    String full_name;

    String email;

    String course_name;

    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    Integer class_id;

    String semester_name;

    String request;

    LocalTime creation_time;

    String status;

    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    Integer student_id;

    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    Integer application_id;

}
