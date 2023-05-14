package com.beaconfire.domain.DTO;

import com.beaconfire.domain.jdbc.*;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class StudentGetClassResponse {

    private WebRegClassDisplay webRegClass;

    private ClassToSemesterDisplay semester;

    private ClassToLectureDisplay lecture;

    private ClassToProfessorDisplay professor;

    private ClassToClassroomDisplay classroom;

    private ClassToPrerequisiteDisplay prerequisites;

    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private List<AdminClassToStudentDisplay> students;
}
