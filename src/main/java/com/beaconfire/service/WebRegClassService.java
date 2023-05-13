package com.beaconfire.service;

import com.beaconfire.dao.*;
import com.beaconfire.dao.hibernate.*;
import com.beaconfire.domain.jdbc.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service
public class WebRegClassService {


    @Autowired
    private WebRegClassDisplayDao webRegClassDisplayDao;


    @Autowired
    private ClassToSemesterDisplayDao classToSemesterDisplayDao;

    @Autowired
    private ClassToClassroomDisplayDao classToClassroomDisplayDao;


    @Autowired
    private ClassToPrerequisiteDisplayDao classToPrerequisiteDisplayDao;


    @Autowired
    private ClassToProfessorDisplayDao classToProfessorDisplayDao;


    @Autowired
    private ClassToLectureDisplayDao classToLectureDisplayDao;



    public WebRegClassDisplay getWebRegClassDisplayByClassId(int class_id) {
        return webRegClassDisplayDao.getWebRegClassDisplayByClassId(class_id);
    }

    public ClassToSemesterDisplay getClassToSemesterDisplayByClassId(int class_id) {
        return classToSemesterDisplayDao.getSemesterbyClassId(class_id);
    }

    public ClassToClassroomDisplay getClassToClassroomDisplayByClassId(int class_id) {
        return classToClassroomDisplayDao.getClassroomByClassId(class_id);
    }

    public ClassToPrerequisiteDisplay getClassToPrerequisiteDisplayByClassId(int class_id) {
        return classToPrerequisiteDisplayDao.getPrerequisiteByClassId(class_id);
    }

    public ClassToProfessorDisplay getClassToProfessorDisplayByClassId(int class_id) {
        return classToProfessorDisplayDao.getProfessorByClassId(class_id);
    }

    public ClassToLectureDisplay getClassToLectureDisplayByClassId(int class_id) {
        return classToLectureDisplayDao.getLectureByClassId(class_id);
    }


}
