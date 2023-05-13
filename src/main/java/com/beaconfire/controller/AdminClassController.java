package com.beaconfire.controller;


import com.beaconfire.domain.jdbc.AdminClassDisplay;
import com.beaconfire.service.AdminClassDisplayService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.time.LocalTime;
import java.util.List;

@Controller
@RequestMapping("/admin/class")
public class AdminClassController {

    @Autowired
    AdminClassDisplayService adminClassDisplayService;

    @GetMapping("/all")
    public String getAllClass(Model model, HttpSession session){
        if(session.getAttribute("userId") == null || !session.getAttribute("is_admin").equals("1")){
            return "redirect:/login";
        }
        List<AdminClassDisplay> adminClassDisplayList = adminClassDisplayService.getAdminClassDisplays();
        for(AdminClassDisplay adminClassDisplay : adminClassDisplayList){
            adminClassDisplay.setIs_active(adminClassDisplay.getIs_active().equals("true") ? "Active" : "Inactive");
        }

        model.addAttribute("adminClassDisplayList", adminClassDisplayList);
        return "adminClass";
    }

    @PostMapping()
    public String createClass(@RequestParam("course_id") int courseId,
                              @RequestParam("professor_id") int professorId,
                              @RequestParam("semester_id") int semesterId,
                              @RequestParam("classroom_id") int classroomId,
                              @RequestParam("capacity") int capacity,
                              @RequestParam("dayOfWeek") String dayOfWeek,
                              @RequestParam("startTime") @DateTimeFormat(pattern = "HH:mm") LocalTime startTime,
                              @RequestParam("endTime") @DateTimeFormat(pattern = "HH:mm") LocalTime endTime, HttpSession session) {

        if(session.getAttribute("userId") == null || !session.getAttribute("is_admin").equals("1")){
            return "redirect:/login";
        }
        System.out.println("testCCC: "+ courseId);
        System.out.println("testCCC: "+ professorId);
        System.out.println("testCCC: "+ semesterId);
        System.out.println("testCCC: "+ classroomId);
        System.out.println("testCCC: "+ capacity);
        System.out.println("testCCC: "+ dayOfWeek);
        System.out.println("testCCC: "+ startTime);
        System.out.println("testCCC: "+ endTime);


        int returnedId = adminClassDisplayService.addNewClass(courseId, professorId, semesterId, classroomId, capacity, dayOfWeek, startTime, endTime);

        // Redirect to a page that shows the newly created class
        return "redirect:/class/" + returnedId;
    }

    @PatchMapping("/{class_id}/{is_active}")
    public String changeClassStatus(@PathVariable("class_id") int classId, @PathVariable("is_active") String is_active, HttpSession session){
        if(session.getAttribute("userId") == null || !session.getAttribute("is_admin").equals("1")){
            return "redirect:/login";
        }
//        adminStudentService.flipStudentStatus(studentId);
        adminClassDisplayService.flipClassStatus(classId);
        return "redirect:/admin/class/all";
    }

}
