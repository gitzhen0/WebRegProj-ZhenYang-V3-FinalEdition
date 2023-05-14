package com.beaconfire.domain.DTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ClassApplicationResponse {

    private String courseCode;

    private String courseName;

    private String semester;

    private LocalDateTime creationTime;

    private String request;

    private String status;


}
