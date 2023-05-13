package com.beaconfire.controller;

import com.beaconfire.domain.jdbc.Department;
import com.beaconfire.service.DepartmentService;
import com.beaconfire.service.StudentService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
@RequestMapping("/signup")
public class SignupController {

    private final StudentService studentService;
    private final DepartmentService departmentService;

    public SignupController(StudentService studentService, DepartmentService departmentService) {
        this.studentService = studentService;
        this.departmentService = departmentService;
    }

    @GetMapping()
    public String getRegistrationPage(Model model){
        List<Department> departments = departmentService.getAllDepartments();
        model.addAttribute("departments", departments);
        return "signUp";
    }

    @PostMapping()
    public String processSignup(@RequestParam("email") String email,
                                @RequestParam("password") String password,
                                @RequestParam("first_name") String first_name,
                                @RequestParam("last_name") String last_name,
                                @RequestParam("department_id") int department_id,
                                HttpSession session,
                                Model model) {

        String StudentHomePage = studentService.registerStudent(first_name, last_name, email, password, department_id, 1, 0, session);
        return StudentHomePage;
    }
}
