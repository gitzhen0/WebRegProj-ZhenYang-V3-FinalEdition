package com.beaconfire.service;

import com.beaconfire.dao.DAOinterface.AdminClassDisplayDao;
import com.beaconfire.dao.DAOinterface.AdminStudentDisplayDao;
import com.beaconfire.dao.DAOinterface.WebRegClassDisplayDao;
import com.beaconfire.domain.hibernate.ClassroomHibernate;
import com.beaconfire.domain.hibernate.ProfessorHibernate;
import com.beaconfire.domain.hibernate.SemesterHibernate;
import com.beaconfire.domain.jdbc.AdminClassDisplay;
import com.beaconfire.domain.jdbc.WebRegClassDisplay;
import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalTime;
import java.util.List;

@Service
public class AdminClassDisplayService {


    @Autowired
    private AdminClassDisplayDao adminClassDisplayDao;


    @Autowired
    private AdminStudentDisplayDao adminStudentDisplayDao;

    @Autowired
    private WebRegClassDisplayDao webRegClassDisplayDao;

    public WebRegClassDisplay getWebRegClassDisplayByClassId(Integer id){
        return webRegClassDisplayDao.getWebRegClassDisplayByClassId(id);
    }


    public List<AdminClassDisplay> getAdminClassDisplays() {
        return adminClassDisplayDao.getAllClasses();
    }

    public int addNewClass(int course_id, int professor_id, int semester_id, int classroom_id, int capacity, Integer dayOfWeek, LocalTime startTime, LocalTime endTime){
        return adminClassDisplayDao.addNewClass(course_id, professor_id, semester_id, classroom_id, capacity, dayOfWeek, startTime, endTime);
    }

    public void flipClassStatus(int classId, int status) {
        adminClassDisplayDao.flipClassStatus(classId, status);
    }

    public boolean courseExistsById(Integer id){
        return adminClassDisplayDao.courseExistsById(id);
    }


    public Boolean semesterExistsById(Integer id) {
        return adminClassDisplayDao.semesterExistsById(id);
    }


    public Boolean professorExistsById(Integer id) {
        return adminClassDisplayDao.professorExistsById(id);
    }


    public Boolean classroomExistsById(Integer id) {
        return adminClassDisplayDao.classroomExistsById(id);
    }

    public Boolean classExistsById(Integer id){
        return adminStudentDisplayDao.classExistsById(id);
    }

}
