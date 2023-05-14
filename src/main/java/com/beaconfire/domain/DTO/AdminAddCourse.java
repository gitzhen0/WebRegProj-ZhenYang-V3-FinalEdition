package com.beaconfire.domain.DTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AdminAddCourse {

    @NotBlank(message = "Course name cannot be blank")
    private String course_name;

    @NotBlank(message = "Course code cannot be blank")
    private String course_code;

    @NotNull(message = "Department ID cannot be null")
    @Min(value = 1, message = "Department ID must be at least 1")
    private Integer department_id;

    @NotBlank(message = "Description cannot be blank")
    private String description;
}
