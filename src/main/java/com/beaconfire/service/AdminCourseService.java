package com.beaconfire.service;

import com.beaconfire.dao.DAOinterface.AdminCourseDisplayDao;
import com.beaconfire.domain.jdbc.AdminClassroom;
import com.beaconfire.domain.jdbc.AdminCourseDisplay;
import com.beaconfire.domain.jdbc.AdminProfessor;
import com.beaconfire.domain.jdbc.AdminSemester;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AdminCourseService {


    @Autowired
    private AdminCourseDisplayDao adminCourseDisplayDao;


    @Cacheable(value = "AdminCourseAll")
    public List<AdminCourseDisplay> getAdminCourseDisplays() {
        return adminCourseDisplayDao.getAllCourses();
    }


    @CacheEvict(value = "AdminCourseAll", allEntries = true)
    public void addNewCourse(String courseName, String courseCode, int departmentId, String description) {
        adminCourseDisplayDao.addNewCourse(courseName, courseCode, departmentId, description);
    }

    public List<AdminCourseDisplay> getAllCourses() {
        return adminCourseDisplayDao.getAllCourses();
    }

    public List<AdminProfessor> getAllProfessors() {
        return adminCourseDisplayDao.getAllProfessors();
    }

    public List<AdminClassroom> getAllClassrooms() {
        return adminCourseDisplayDao.getAllClassrooms();
    }

    public List<AdminSemester> getAllSemesters() {
        return adminCourseDisplayDao.getAllSemesters();
    }

}
