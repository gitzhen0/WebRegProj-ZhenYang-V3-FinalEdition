package com.beaconfire.domain.DTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AdminAddClass {

    private Integer course_id;
    private Integer semester_id;
    private Integer professor_id;

    private Integer classroom_id;

    private Integer capacity;

    private LocalTime lecture_start_time;

    private LocalTime lecture_end_time;

    @Min(value = 1, message = "Min value is 1 for day of week")
    @Max(value = 7, message = "Max value is 7 for day of week")
    private Integer lecture_day_of_week;

}
