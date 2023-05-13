package com.beaconfire.domain.jdbc;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ClassToClassroomDisplay {

    int class_id;

    String name;

    String building;

    int capacity;

}
