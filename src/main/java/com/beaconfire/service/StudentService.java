package com.beaconfire.service;

import com.beaconfire.dao.DAOinterface.StudentDao;
import com.beaconfire.domain.hibernate.StudentHibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class StudentService implements UserDetailsService {


    @Autowired
    private StudentDao studentDao;//add

    @Autowired
    private PasswordEncoder passwordEncoder;


    @CacheEvict(value = "AllAdminStudents", allEntries = true)
    public Integer registerStudent(String first_name, String last_name, String email, String password, int department_id, int is_active, int is_admin){
        try {
            password = passwordEncoder.encode(password); // use password encoder here
            int stuId = studentDao.registerStudent(first_name, last_name, email, password, department_id, is_active, is_admin);

            return stuId;
        } catch (Exception e) {
            return -1;
        }
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        StudentHibernate student = studentDao.getStudentByEmail2(email);
        if (student == null) {
            throw new UsernameNotFoundException("User Not Found with email: " + email);
        }

        return new User(student.getEmail(), student.getPassword(),
                student.getIs_admin() == 1 ? AuthorityUtils.createAuthorityList("ADMIN") : AuthorityUtils.createAuthorityList("STUDENT"));
    }

    public StudentHibernate getStudentByEmail2(String email){
        return studentDao.getStudentByEmail2(email);
    }



}
