package com.beaconfire.domain.jdbc;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class StudentClass {
    int id;
    int student_id;

    int class_id;

    String status;
}
