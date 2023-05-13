package com.beaconfire.domain.jdbc;

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

    int class_id;

    private LocalDate start_date;
    private LocalDate end_date;
    private String semester_name;


}
