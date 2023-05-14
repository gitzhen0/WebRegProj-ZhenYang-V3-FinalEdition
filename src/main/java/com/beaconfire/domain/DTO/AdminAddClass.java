package com.beaconfire.domain.DTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.time.LocalTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AdminAddClass {

    @NotNull(message = "Course ID cannot be null")
    @Min(value = 1, message = "Course ID must be at least 1")
    private Integer course_id;

    @NotNull(message = "Semester ID cannot be null")
    @Min(value = 1, message = "Semester ID must be at least 1")
    private Integer semester_id;

    @NotNull(message = "Professor ID cannot be null")
    @Min(value = 1, message = "Professor ID must be at least 1")
    private Integer professor_id;

    @NotNull(message = "Classroom ID cannot be null")
    @Min(value = 1, message = "Classroom ID must be at least 1")
    private Integer classroom_id;

    @NotNull(message = "Capacity cannot be null")
    @Min(value = 1, message = "Capacity must be at least 1")
    private Integer capacity;

    @NotNull(message = "Lecture start time cannot be null")
    private LocalTime lecture_start_time;

    @NotNull(message = "Lecture end time cannot be null")
    private LocalTime lecture_end_time;

    @NotNull(message = "Lecture day of week cannot be null")
    @Min(value = 1, message = "Min value is 1 for day of week")
    @Max(value = 7, message = "Max value is 7 for day of week")
    private Integer lecture_day_of_week;

}

