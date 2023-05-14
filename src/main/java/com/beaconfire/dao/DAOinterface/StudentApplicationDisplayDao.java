package com.beaconfire.dao.DAOinterface;

import com.beaconfire.domain.DTO.ClassApplicationResponse;
import com.beaconfire.domain.jdbc.SingleApplication;
import com.beaconfire.domain.jdbc.StudentApplicationDisplay;

import java.util.List;

public interface StudentApplicationDisplayDao {

    List<StudentApplicationDisplay> getApplicationsByStudentId(int student_id);

    ClassApplicationResponse addNewApplication(int studentId, int classId, String request);

    void removeApplication(int studentId, int classId);

    void removeApplicationById(int applicationId);

    void removeStudentFromClass(int studentId, int classId);

    boolean applicationExists(int studentId, int classId, String request);

    String[] validToRemove(int studentId, int applicationId);

    SingleApplication getApplicationById(int id);

    Boolean applicationExistsById(int id);

    Boolean applicationIsPendingById(int applicationId);
}
