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
public class ClassToPrerequisiteDisplay {

    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private Integer class_id;

    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private Integer prerequisite_id;

    private String prerequisite_name;


}
