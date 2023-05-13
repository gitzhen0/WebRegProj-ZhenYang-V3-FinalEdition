package com.beaconfire.controller;

import com.beaconfire.domain.jdbc.AdminApplication;
import com.beaconfire.service.StudentApplicationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
@RequestMapping("/admin/application")
public class AdminApplicationController {

    @Autowired
    StudentApplicationService studentApplicationService;

    @GetMapping("/all")
    public String getAllApplication(HttpSession session, Model model){
        if(session.getAttribute("userId") == null || !session.getAttribute("is_admin").equals("1")){
            return "redirect:/login";
        }
        List<AdminApplication> adminApplicationList = studentApplicationService.getAllApplications();
        model.addAttribute("adminApplicationList", adminApplicationList);
        return "adminApplication";
    }

    @PatchMapping("/{application_id}")
    public String changeApplicationStatus(HttpSession session,
                                          @PathVariable("application_id") int applicationId,
                                          @RequestParam("status") String status,
                                          @RequestParam(value = "feedback", required = false) String feedback) {
        if (session.getAttribute("userId") == null || !session.getAttribute("is_admin").equals("1")) {
            return "redirect:/login";
        }
        if(feedback.isEmpty() || feedback == null){
            feedback = "";
        }

        studentApplicationService.updateApplicationStatus(applicationId, status, feedback);

        if(status.equals("approved")){
            studentApplicationService.processApprovedApplication(applicationId);
        }

        return "redirect:/admin/application/all";
    }
}
