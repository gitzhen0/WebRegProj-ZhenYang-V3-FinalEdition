package com.beaconfire.domain.jdbc;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ClassToSemesterDisplay {

    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    Integer class_id;

    private LocalDate start_date;
    private LocalDate end_date;
    private String semester_name;


}
