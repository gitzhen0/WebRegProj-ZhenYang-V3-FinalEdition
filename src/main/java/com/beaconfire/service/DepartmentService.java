package com.beaconfire.service;

import com.beaconfire.dao.DAOinterface.DepartmentDao;
import com.beaconfire.domain.jdbc.Department;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DepartmentService {

    @Autowired
    private DepartmentDao departmentDao;


    public List<Department> getAllDepartments(){
        return departmentDao.getAllDepartments();
    }
}
