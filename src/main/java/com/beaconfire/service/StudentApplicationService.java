package com.beaconfire.service;

import com.beaconfire.dao.DAOinterface.AdminApplicationDao;
import com.beaconfire.dao.DAOinterface.StudentApplicationDisplayDao;
import com.beaconfire.domain.DTO.ClassApplicationResponse;
import com.beaconfire.domain.jdbc.AdminApplication;
import com.beaconfire.domain.jdbc.SingleApplication;
import com.beaconfire.domain.jdbc.StudentApplicationDisplay;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
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

    @Cacheable(value = "allApplications")
    public List<AdminApplication> getAllApplications(){
        return adminApplicationDao.getAllApplications();
    }

    @CacheEvict(value = "allApplications", allEntries = true)
    public ClassApplicationResponse addNewApplication(int studentId, int classId, String request) {
        return studentApplicationDao.addNewApplication(studentId, classId, request);
    }

    @CacheEvict(value = "allApplications", allEntries = true)
    public void removeApplicationById(int applicationId){
        studentApplicationDao.removeApplicationById(applicationId);
    }

    @CacheEvict(value = "allApplications", allEntries = true)
    public void updateApplicationStatus(Integer applicationId, String status, String description) {
        adminApplicationDao.updateApplicationStatus(applicationId, status, description);
    }

    @CacheEvict(value = "allApplications", allEntries = true)
    public void processApprovedApplication(int applicationId) {
        String request = adminApplicationDao.getRequestByApplicationId(applicationId);
        if(request.equals("add")){
            adminApplicationDao.addStudentToClassByApplicationId(applicationId);
        } else if(request.equals("withdraw")){
            adminApplicationDao.withdrawStudentFromClassByApplicationId(applicationId);
        }
    }


    public void removeStudentFromClass(int studentId, int classId) {
        studentApplicationDao.removeStudentFromClass(studentId, classId);
    }

    public boolean applicationExists(int studentId, int classId, String request){
        return studentApplicationDao.applicationExists(studentId, classId, request);
    }

    public String[] validToRemove(int studentId, int applicationId){
        return studentApplicationDao.validToRemove(studentId, applicationId);
    }

    public SingleApplication getApplicationById(int id){
        return studentApplicationDao.getApplicationById(id);
    }

    public Boolean applicationExistsById(int id) {
        return studentApplicationDao.applicationExistsById(id);
    }

    public Boolean applicationIsPendingById(int applicationId) {
        return studentApplicationDao.applicationIsPendingById(applicationId);
    }
}
