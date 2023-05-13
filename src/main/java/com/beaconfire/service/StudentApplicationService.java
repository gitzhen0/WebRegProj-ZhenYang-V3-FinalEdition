package com.beaconfire.service;

import com.beaconfire.dao.AdminApplicationDao;
import com.beaconfire.dao.StudentApplicationDisplayDao;
import com.beaconfire.domain.jdbc.AdminApplication;
import com.beaconfire.domain.jdbc.StudentApplicationDisplay;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StudentApplicationService {


    @Autowired
    private StudentApplicationDisplayDao studentApplicationDao;

    @Autowired
    private AdminApplicationDao adminApplicationDao;



    public List<StudentApplicationDisplay> getStudentApplicationDisplay(int studentId){
        return studentApplicationDao.getApplicationsByStudentId(studentId);
    }

    public List<AdminApplication> getAllApplications(){
        return adminApplicationDao.getAllApplications();
    }

    public void addNewApplication(int studentId, int classId, String request) {
        studentApplicationDao.addNewApplication(studentId, classId, request);
    }

    public void removeApplication(int studentId, int classId) {
        studentApplicationDao.removeApplication(studentId, classId);
    }

    public void removeApplicationById(int applicationId){
        studentApplicationDao.removeApplicationById(applicationId);
    }

    public void updateApplicationStatus(Integer applicationId, String status, String description) {
        adminApplicationDao.updateApplicationStatus(applicationId, status, description);
    }

    public void processApprovedApplication(int applicationId) {
        String request = adminApplicationDao.getRequestByApplicationId(applicationId);
        if(request.equals("add")){
            adminApplicationDao.addStudentToClassByApplicationId(applicationId);
        } else if(request.equals("withdraw")){
            adminApplicationDao.withdrawStudentFromClassByApplicationId(applicationId);
        }
    }

    public void withdrawStudentFromClassByApplicationId(Integer applicationId) {
        adminApplicationDao.withdrawStudentFromClassByApplicationId(applicationId);
    }

    public void removeStudentFromClass(int studentId, int classId) {
        studentApplicationDao.removeStudentFromClass(studentId, classId);
    }
}
