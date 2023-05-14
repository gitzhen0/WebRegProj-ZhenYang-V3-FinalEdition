package com.beaconfire.domain.jdbc;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ClassToClassroomDisplay {

    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    Integer class_id;

    String name;

    String building;

    Integer capacity;

}
