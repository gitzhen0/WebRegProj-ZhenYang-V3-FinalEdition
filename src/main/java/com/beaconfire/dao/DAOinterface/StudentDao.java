package com.beaconfire.dao.DAOinterface;

import com.beaconfire.domain.hibernate.StudentHibernate;
import com.beaconfire.domain.jdbc.Student;

public interface StudentDao{

    int registerStudent(String first_name, String last_name, String email, String password, int department_id, int is_active, int is_admin);

    Student getStudentByEmail(String email);

    StudentHibernate getStudentByEmail2(String email);
}
