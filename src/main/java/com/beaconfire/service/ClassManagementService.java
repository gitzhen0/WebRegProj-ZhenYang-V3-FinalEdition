package com.beaconfire.service;

import com.beaconfire.dao.ClassManagementDisplayDao;
import com.beaconfire.dao.hibernate.ClassManagementDisplayDaoHibernateImpl;
import com.beaconfire.domain.jdbc.ClassManagementDisplay;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
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
