package com.beaconfire.controller;

import com.beaconfire.domain.jdbc.AdminHomeDisplay;
import com.beaconfire.domain.jdbc.StudentClassDisplay;
import com.beaconfire.service.AdminStudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
@RequestMapping("/admin/student")
public class AdminStudentController {

    @Autowired
    AdminStudentService adminStudentService;



    @GetMapping("/{student_id}")
    public String getStudentById(@PathVariable("student_id") int student_id, HttpSession session, Model model){
        if(session.getAttribute("userId") == null || !session.getAttribute("is_admin").equals("1")){
            return "redirect:/login";
        }
        AdminHomeDisplay adminHomeDisplay = adminStudentService.getAdminHomeDisplay(student_id);
        model.addAttribute("adminHomeDisplay", adminHomeDisplay);

        List<StudentClassDisplay> studentClassDisplayList = adminStudentService.getStudentClassByStudentId(student_id);
        model.addAttribute("studentClassDisplayList", studentClassDisplayList);

        return "adminStudent";
    }

    @PatchMapping("/{student_id}/{is_active}")
    public String changeStudentStatus(@PathVariable("student_id") int studentId, @PathVariable("is_active") String is_active, HttpSession session){
        if(session.getAttribute("userId") == null || !session.getAttribute("is_admin").equals("1")){
            return "redirect:/login";
        }
        adminStudentService.flipStudentStatus(studentId);
        return "redirect:/admin/home";
    }

    @PatchMapping("/{student_id}/class/{class_id}")
    public String changeStudentClassStatus(@PathVariable("student_id") int studentId, @PathVariable("class_id") int classId, @RequestParam("status") String status, HttpSession session){
        if(session.getAttribute("userId") == null || !session.getAttribute("is_admin").equals("1")){
            return "redirect:/login";
        }
        adminStudentService.changeStudentClassStatus(studentId, classId, status);
        return "redirect:/admin/student/" + studentId;
    }
}
