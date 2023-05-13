package com.beaconfire.dao;

import com.beaconfire.domain.jdbc.Department;

import java.util.List;

public interface DepartmentDao {

    List<Department> getAllDepartments();
}
