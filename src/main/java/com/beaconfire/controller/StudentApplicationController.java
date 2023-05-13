package com.beaconfire.controller;

import com.beaconfire.domain.jdbc.StudentApplicationDisplay;
import com.beaconfire.service.StudentApplicationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
@RequestMapping("/application")
public class StudentApplicationController {

    @Autowired
    private final StudentApplicationService studentApplicationService;

    public StudentApplicationController(StudentApplicationService studentApplicationService) {
        this.studentApplicationService = studentApplicationService;
    }

    @GetMapping("/all")
    public String getStudentApplicationById(HttpSession session, Model model){
        if(session.getAttribute("userId") == null){
            return "redirect:/login";
        }
        int userId = (int) session.getAttribute("userId");
        List<StudentApplicationDisplay> studentApplicationDisplays = studentApplicationService.getStudentApplicationDisplay(userId);

        model.addAttribute("studentApplicationDisplays", studentApplicationDisplays);
       return "studentApplication";
    }

    @DeleteMapping("/{application_id}")
    public String removeApplication(@PathVariable("application_id") int applicationId, HttpSession session, Model model){
        if(session.getAttribute("userId") == null){
            return "redirect:/login";
        }
        studentApplicationService.removeApplicationById(applicationId);
        return "redirect:/application/all";
    }
}
