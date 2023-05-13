package com.beaconfire.controller;


import com.beaconfire.service.StudentClassService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("/home")
public class StudentHomeController {

    @Autowired
    private final StudentClassService studentClassService;

    public StudentHomeController(StudentClassService studentClassService) {
        this.studentClassService = studentClassService;
    }

    @GetMapping()
    public String getStudentClasses(
            @RequestParam(required = false, defaultValue = "1") int page,
            @RequestParam(required = false, defaultValue = "4") int limit,
            HttpSession session,
            Model model) {

        return studentClassService.displayStudentClass(page, limit, session, model);
    }
}
