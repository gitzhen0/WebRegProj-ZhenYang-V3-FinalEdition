package com.beaconfire.service;

import com.beaconfire.Utils.PasswordUtils;
import com.beaconfire.dao.StudentDao;
import com.beaconfire.domain.DTO.SignupResponse;
import com.beaconfire.domain.hibernate.StudentHibernate;
import com.beaconfire.domain.jdbc.Student;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;

@Service
public class StudentService implements UserDetailsService {


    @Autowired
    private StudentDao studentDao;//add

    @Autowired
    private PasswordEncoder passwordEncoder;


    public Integer registerStudent(String first_name, String last_name, String email, String password, int department_id, int is_active, int is_admin){
        try {
            password = passwordEncoder.encode(password); // use password encoder here
            int stuId = studentDao.registerStudent(first_name, last_name, email, password, department_id, is_active, is_admin);

            return stuId;
        } catch (Exception e) {
            return -1;
        }
    }


//    public String login(String email, String password, HttpSession session){
//        Student student = studentDao.getStudentByEmail(email);
//        System.out.println("password: " + password);
//        password = PasswordUtils.md5(password);
//        if (student == null) {
//            return "login";
//        }
//        if (student.getPassword().equals(password)){
//            session.setAttribute("userId", studentDao.getStudentByEmail(email).getId());
//            int isAdmin = studentDao.getStudentByEmail(email).getIs_admin();
//            session.setAttribute("is_admin", String.valueOf(isAdmin));
//            System.out.println("MY Student ID in Session: " + session.getAttribute("userId"));
//            System.out.println("isAdmin: "+ isAdmin);
//            if(isAdmin == 1){
//                return "redirect:/admin/home";
//            }else{
//                return "redirect:/home";
//            }
//        }
//        return "login";
//    }

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
