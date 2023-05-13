package com.beaconfire.dao;

import com.beaconfire.domain.jdbc.AdminClassroom;
import com.beaconfire.domain.jdbc.AdminCourseDisplay;
import com.beaconfire.domain.jdbc.AdminProfessor;
import com.beaconfire.domain.jdbc.AdminSemester;

import java.util.List;

public interface AdminCourseDisplayDao {

    List<AdminCourseDisplay> getAllCourses();

    List<AdminProfessor> getAllProfessors();

    List<AdminClassroom> getAllClassrooms();

    List<AdminSemester> getAllSemesters();

    void addNewCourse(String courseName, String courseCode, int departmentId, String description);
}
