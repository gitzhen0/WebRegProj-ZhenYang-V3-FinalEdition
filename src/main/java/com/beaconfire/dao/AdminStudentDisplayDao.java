package com.beaconfire.dao;

import com.beaconfire.domain.jdbc.AdminHomeDisplay;
import com.beaconfire.domain.jdbc.StudentClassDisplay;

import java.util.List;

public interface AdminStudentDisplayDao {

    AdminHomeDisplay getStudent(int studentId);

    String UpdateStudentStatus(int studentId, int status);

    List<StudentClassDisplay> getStudentClassesByStudentId(int studentId);

    void flipStudentStatus(int studentId);

    void changeStudentClassStatus(int studentId, int classId, String status);
}
