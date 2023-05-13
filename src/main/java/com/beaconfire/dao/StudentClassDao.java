package com.beaconfire.dao;

import com.beaconfire.domain.jdbc.StudentClass;

import java.util.List;

public interface StudentClassDao {

    List<StudentClass> getStudentClassByStudentId(int studentId);

    int check0IsStudentEnrolledInClass(int studentId, int classId);

    int checkAStudentIsActive(int studentId);

    int checkBClassIsActive(int classId);

    int checkCStudentPassPrerequisite(int studentId, int classId);

    int checkDNoLectureConflict(int studentId, int classId);

    int checkEClassIsFull(int classId);

    int checkFIfStudentNeverPassed(int studentId, int courseId);

    int checkXisStudentEnrolledInClassWithinTwoWeeks(int studentId, int classId);

    void addStudentToClass(int studentId, int classId);
}
