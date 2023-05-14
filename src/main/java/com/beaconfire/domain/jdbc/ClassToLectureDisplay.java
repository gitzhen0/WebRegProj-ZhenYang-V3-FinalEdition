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
public class ClassToLectureDisplay {
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    Integer class_id;
    String day_of_the_week;

    LocalTime start_time;

    LocalTime end_time;
}
