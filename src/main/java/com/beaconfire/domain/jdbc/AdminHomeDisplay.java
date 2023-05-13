package com.beaconfire.domain.jdbc;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class AdminHomeDisplay { // display detailed info for each student

    int student_id;// id of each student

    String first_name;

    String last_name;

    String email;

    String department_name;

    String school_name;

    String is_active;

}
