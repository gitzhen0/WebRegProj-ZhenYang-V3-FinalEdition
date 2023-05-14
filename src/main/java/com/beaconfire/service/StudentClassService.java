package com.beaconfire.service;

import com.beaconfire.dao.StudentClassDao;
import com.beaconfire.dao.StudentClassDisplayDao;
import com.beaconfire.dao.hibernate.StudentClassDisplayDaoHibernateImpl;
import com.beaconfire.domain.jdbc.AdminClassToStudentDisplay;
import com.beaconfire.domain.jdbc.StudentClassDisplay;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import javax.servlet.http.HttpSession;
import java.util.List;

@Service
public class StudentClassService {


    @Autowired
    private StudentClassDao studentClassDao;


    @Autowired
    private StudentClassDisplayDao studentClassDisplayDao;



    public List<StudentClassDisplay> displayStudentClass(int page, int limit, int id){
        List<StudentClassDisplay> studentClasses = studentClassDisplayDao.findPaginated(page, limit, id);
        int totalPages = studentClassDisplayDao.getTotalPages(limit, id); // don't really need this field



        return studentClasses;
    }

    public List<AdminClassToStudentDisplay> getStudentsByClassId(int classId){
        return studentClassDisplayDao.findStudentsByClassId(classId);
    }


    public String[] applicationCheck(int studentId, int classId){
        String[] result = new String[8];
        // index 0 for Student enrollment status is fine (not enrolled)
        result[0] = "0 - Student is already enrolled in this class";
        // index 1 for Student is active
        result[1] = "1 - Student is not active";
        // index 2 for Class is active
        result[2] = "2 - Class is not active";
        // index 3 for Student pass prerequisite
        result[3] = "3 - Student does not pass prerequisite";
        // index 4 for No lecture conflict
        result[4] = "4 - Student has lecture conflict";
        // index 5 for Class capacity is fine
        result[5] = "5 - Class is full";
        // index 6 for student never passed this course already
        result[6] = "6 - Student has already passed this course";
        // index 7 for Student enrolled within 2 weeks of semester start
        result[7] = "7 - Student enrolled out of 2 weeks of semester start";

        if(studentClassDao.check0IsStudentEnrolledInClass(studentId, classId) == 0){
            result[0] = "";
        }
        if(studentClassDao.checkAStudentIsActive(studentId) == 1){
            result[1] = "";
        }
        if(studentClassDao.checkBClassIsActive(classId) == 1){
            result[2] = "";
        }
        if(studentClassDao.checkCStudentPassPrerequisite(studentId, classId) == 1){
            result[3] = "";
        }
        if(studentClassDao.checkDNoLectureConflict(studentId, classId) == 1){
            result[4] = "";
        }
        if(studentClassDao.checkEClassIsFull(classId) == 1){
            result[5] = "";
        }
        if(studentClassDao.checkFIfStudentNeverPassed(studentId, classId) == 1){
            result[6] = "";
        }
        if(studentClassDao.checkXisStudentEnrolledInClassWithinTwoWeeks(studentId, classId) == 1){
            result[7] = "";
        }

        return result;

    }

    public void addStudentToClass(int studentId, int classId){
        studentClassDao.addStudentToClass(studentId, classId);
    }






}
