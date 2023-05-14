package com.beaconfire.dao.DAOinterface;

import com.beaconfire.domain.jdbc.AdminClassDisplay;

import java.time.LocalTime;
import java.util.List;

public interface AdminClassDisplayDao {

    List<AdminClassDisplay> getAllClasses();

    int addNewClass(int course_id, int professor_id, int semester_id, int classroom_id, int capacity, Integer dayOfWeek, LocalTime startTime, LocalTime endTime);

    void flipClassStatus(int classId, int status);


    Boolean courseExistsById(Integer id);

    Boolean semesterExistsById(Integer id);

    Boolean professorExistsById(Integer id);

    Boolean classroomExistsById(Integer id);
}
