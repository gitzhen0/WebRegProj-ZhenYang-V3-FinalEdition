package com.beaconfire.dao.DAOinterface;

import com.beaconfire.domain.jdbc.Department;

import java.util.List;

public interface DepartmentDao {

    List<Department> getAllDepartments();

    Boolean departmentExistsById(Integer id);
}
