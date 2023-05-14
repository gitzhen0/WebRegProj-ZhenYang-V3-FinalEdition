package com.beaconfire.controller;

import com.beaconfire.domain.DTO.AdminStudentDetailResponse;
import com.beaconfire.domain.DTO.GeneralResponse;
import com.beaconfire.domain.jdbc.AdminHomeDisplay;
import com.beaconfire.domain.jdbc.StudentClassDisplay;
import com.beaconfire.service.AdminStudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
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
    public ResponseEntity getStudentById(@PathVariable("student_id") int student_id){


        if(!adminStudentService.studentExistsById(student_id)){
            return ResponseEntity.badRequest().body(new GeneralResponse<>(GeneralResponse.Status.FAILED, "student with id: " + student_id +", doesn't exist", null));
        }

        AdminHomeDisplay adminHomeDisplay = adminStudentService.getAdminHomeDisplay(student_id);

        List<StudentClassDisplay> studentClassDisplayList = adminStudentService.getStudentClassByStudentId(student_id);

        studentClassDisplayList.stream().forEach(scdl ->{
            scdl.setClass_id(null);
            scdl.setStudent_id(null);
        });

        AdminStudentDetailResponse builder = AdminStudentDetailResponse.builder()
                .email(adminHomeDisplay.getEmail())
                .full_name(adminHomeDisplay.getFirst_name() + " " + adminHomeDisplay.getLast_name())
                .department_name(adminHomeDisplay.getDepartment_name())
                .school(adminHomeDisplay.getSchool_name())
                .isActive(adminHomeDisplay.getIs_active())
                .registered_classes(studentClassDisplayList)
                .build();

        return ResponseEntity.ok().body(new GeneralResponse<AdminStudentDetailResponse>(GeneralResponse.Status.SUCCESS, "success message", builder));
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
