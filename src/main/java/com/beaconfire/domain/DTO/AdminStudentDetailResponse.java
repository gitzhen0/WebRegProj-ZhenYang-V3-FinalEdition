package com.beaconfire.domain.DTO;

import com.beaconfire.domain.jdbc.StudentClassDisplay;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AdminStudentDetailResponse {

    private String full_name;

    private String email;

    private String department_name;

    private String school;

    private String isActive;

    List<StudentClassDisplay> registered_classes;
}
