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
public class ClassToLectureDisplay {
    int class_id;
    String day_of_the_week;

    LocalTime start_time;

    LocalTime end_time;
}
