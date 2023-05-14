package com.beaconfire.service;

import com.beaconfire.dao.DAOinterface.ClassManagementDisplayDao;
import com.beaconfire.domain.jdbc.ClassManagementDisplay;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ClassManagementService {


    @Autowired
    ClassManagementDisplayDao classManagementDisplayDao;



    public List<ClassManagementDisplay> getClassManagementDisplay(int classId){
        return classManagementDisplayDao.getClassManagementDisplayByStudentId(classId);
    }
}
