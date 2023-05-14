package com.beaconfire.dao.DAOinterface;

import com.beaconfire.domain.jdbc.AdminHomeDisplay;
import com.beaconfire.domain.jdbc.StudentClassDisplay;

import java.util.List;

public interface AdminStudentDisplayDao {

    AdminHomeDisplay getStudent(int studentId);

    String UpdateStudentStatus(int studentId, int status);

    List<StudentClassDisplay> getStudentClassesByStudentId(int studentId);

    Boolean flipStudentStatus(int studentId, int status);

    void changeStudentClassStatus(int studentId, int classId, String status);

    Boolean studentExistsById(Integer studentId);

    Boolean classExistsById(Integer classId);

    Boolean isStudentEnrolledInClass(Integer studentId, Integer classId);
}
