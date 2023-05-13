package com.beaconfire.dao;

import com.beaconfire.domain.jdbc.AdminApplication;

import java.util.List;

public interface AdminApplicationDao {

    List<AdminApplication> getAllApplications();

    void updateApplicationStatus(Integer applicationId, String status, String description);

    String getRequestByApplicationId(Integer applicationId);

    void addStudentToClassByApplicationId(Integer applicationId);

    void withdrawStudentFromClassByApplicationId(Integer applicationId);

}
