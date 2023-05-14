package com.beaconfire.controller;


import com.beaconfire.domain.DTO.AdminAddClass;
import com.beaconfire.domain.DTO.GeneralResponse;
import com.beaconfire.domain.jdbc.AdminClassDisplay;
import com.beaconfire.domain.jdbc.WebRegClassDisplay;
import com.beaconfire.service.AdminClassDisplayService;

import org.junit.internal.runners.statements.Fail;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
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
    public ResponseEntity<?> getAllClass(){
        List<AdminClassDisplay> adminClassDisplayList = adminClassDisplayService.getAdminClassDisplays();
        for(AdminClassDisplay adminClassDisplay : adminClassDisplayList){
            adminClassDisplay.setIs_active(adminClassDisplay.getIs_active().equals("true") ? "Active" : "Inactive");
            adminClassDisplay.setClass_id(null);
            adminClassDisplay.setCourse_id(null);
        }
        return ResponseEntity.ok().body(new GeneralResponse<List<AdminClassDisplay>>(GeneralResponse.Status.SUCCESS, "find all classes succcess", adminClassDisplayList));
    }

    @PostMapping()
    public ResponseEntity<?> createClass(@RequestBody AdminAddClass input) {

        if(!adminClassDisplayService.courseExistsById(input.getCourse_id())){
            return ResponseEntity.ok().body(new GeneralResponse<>(GeneralResponse.Status.FAILED, "course id doesn't exists", null));
        }
        if(!adminClassDisplayService.professorExistsById(input.getProfessor_id())){
            return ResponseEntity.ok().body(new GeneralResponse<>(GeneralResponse.Status.FAILED, "professor id doesn't exists", null));
        }
        if(!adminClassDisplayService.semesterExistsById(input.getSemester_id())){
            return ResponseEntity.ok().body(new GeneralResponse<>(GeneralResponse.Status.FAILED, "semester id doesn't exists", null));
        }
        if(!adminClassDisplayService.classroomExistsById(input.getClassroom_id())){
            return ResponseEntity.ok().body(new GeneralResponse<>(GeneralResponse.Status.FAILED, "classroom id doesn't exists", null));
        }

        int returnedId = adminClassDisplayService.addNewClass(input.getCourse_id(), input.getProfessor_id(), input.getSemester_id(), input.getClassroom_id(), input.getCapacity(), input.getLecture_day_of_week(), input.getLecture_start_time(), input.getLecture_end_time());

        // Redirect to a page that shows the newly created class
        return ResponseEntity.ok().body(new GeneralResponse<AdminAddClass>(GeneralResponse.Status.SUCCESS, "Successfully Created Class, Class Id is: " + returnedId+"", input));
    }

    @PatchMapping("/{class_id}/active")
    public ResponseEntity<?> changeClassStatusToActive(@PathVariable("class_id") int classId){
        if(!adminClassDisplayService.classExistsById(classId)){
            return ResponseEntity.badRequest().body(new GeneralResponse<>(GeneralResponse.Status.FAILED, "class id not found", null));
        }

        adminClassDisplayService.flipClassStatus(classId, 1);
        WebRegClassDisplay result = adminClassDisplayService.getWebRegClassDisplayByClassId(classId);
        return ResponseEntity.ok().body(new GeneralResponse<>(GeneralResponse.Status.SUCCESS, "Class set to active success", result));
    }

    @PatchMapping("/{class_id}/inactive")
    public ResponseEntity<?> changeClassStatusToInActive(@PathVariable("class_id") int classId){
        if(!adminClassDisplayService.classExistsById(classId)){
            return ResponseEntity.badRequest().body(new GeneralResponse<>(GeneralResponse.Status.FAILED, "class id not found", null));
        }

        adminClassDisplayService.flipClassStatus(classId, 0);
        WebRegClassDisplay result = adminClassDisplayService.getWebRegClassDisplayByClassId(classId);
        return ResponseEntity.ok().body(new GeneralResponse<>(GeneralResponse.Status.SUCCESS, "Class set to inactive success", result));
    }

}
