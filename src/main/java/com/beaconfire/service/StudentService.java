package com.beaconfire.service;

import com.beaconfire.Utils.PasswordUtils;
import com.beaconfire.dao.StudentDao;
import com.beaconfire.domain.jdbc.Student;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;

@Service
public class StudentService {


    @Autowired
    private StudentDao studentDao;//add



    public String registerStudent(String first_name, String last_name, String email, String password, int department_id, int is_active, int is_admin,  HttpSession session){
        try {
            password = PasswordUtils.md5(password);
            int stuId = studentDao.registerStudent(first_name, last_name, email, password, department_id, is_active, is_admin);

            session.setAttribute("userId", stuId);
            session.setAttribute("is_admin", "0");

            System.out.println("Current User Session ID: " +stuId);
            return "redirect:/home";
        } catch (Exception e) {
            return "signUp";
        }

    }


    public String login(String email, String password, HttpSession session){
        Student student = studentDao.getStudentByEmail(email);
        System.out.println("password: " + password);
        password = PasswordUtils.md5(password);
        if (student == null) {
            return "login";
        }
        if (student.getPassword().equals(password)){
            session.setAttribute("userId", studentDao.getStudentByEmail(email).getId());
            int isAdmin = studentDao.getStudentByEmail(email).getIs_admin();
            session.setAttribute("is_admin", String.valueOf(isAdmin));
            System.out.println("MY Student ID in Session: " + session.getAttribute("userId"));
            System.out.println("isAdmin: "+ isAdmin);
            if(isAdmin == 1){
                return "redirect:/admin/home";
            }else{
                return "redirect:/home";
            }
        }
        return "login";
    }
}
