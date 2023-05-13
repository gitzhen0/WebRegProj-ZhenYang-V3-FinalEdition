package com.beaconfire.dao;

import com.beaconfire.domain.jdbc.AdminClassDisplay;

import java.time.LocalTime;
import java.util.List;

public interface AdminClassDisplayDao {

    List<AdminClassDisplay> getAllClasses();

    int addNewClass(int course_id, int professor_id, int semester_id, int classroom_id, int capacity, String dayOfWeek, LocalTime startTime, LocalTime endTime);

    void flipClassStatus(int classId);



}
