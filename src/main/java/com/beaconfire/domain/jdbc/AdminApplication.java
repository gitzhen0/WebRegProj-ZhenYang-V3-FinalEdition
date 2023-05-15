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

    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    String full_name;

    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    String email;

    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    String course_name;

    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    Integer class_id;

    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    String semester_name;

    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    String request;

    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    LocalTime creation_time;

    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    String status;

    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    Integer student_id;

    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    Integer application_id;

}
