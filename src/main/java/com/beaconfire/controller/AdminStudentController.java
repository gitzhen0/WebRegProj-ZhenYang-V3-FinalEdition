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
    public ResponseEntity<?> changeStudentStatus(@PathVariable("student_id") int studentId, @PathVariable("is_active") String is_active){
        if(!adminStudentService.studentExistsById(studentId)){
            return ResponseEntity.badRequest().body(new GeneralResponse<>(GeneralResponse.Status.FAILED, "student with id: " + studentId +", doesn't exist", null));
        }

        if(is_active.equals("active")){
            Boolean changed = adminStudentService.flipStudentStatus(studentId, 1);
            if(changed){
                return ResponseEntity.ok().body(new GeneralResponse<>(GeneralResponse.Status.SUCCESS, "Student is activated successfully", null));
            }else{
                return ResponseEntity.badRequest().body(new GeneralResponse<>(GeneralResponse.Status.FAILED, "Student is already ACTIVE, no need to activate again", null));
            }
        }else if(is_active.equals("inactive")){
            Boolean changed = adminStudentService.flipStudentStatus(studentId, 0);
            if(changed){
                return ResponseEntity.ok().body(new GeneralResponse<>(GeneralResponse.Status.SUCCESS, "Student is deactivated successfully", null));
            }else{
                return ResponseEntity.badRequest().body(new GeneralResponse<>(GeneralResponse.Status.FAILED, "Student is already INACTIVE, no need to deactivate again", null));
            }
        }else{
            return ResponseEntity.badRequest().body(new GeneralResponse<>(GeneralResponse.Status.FAILED, "please enter either 'active' or 'inactive' for the isActive field", null));
        }
    }

    @PatchMapping("/{student_id}/class/{class_id}/pass")
    public ResponseEntity<?> changeStudentClassStatusToPass(@PathVariable("student_id") int studentId, @PathVariable("class_id") int classId){
        if (!adminStudentService.studentExistsById(studentId)) {
            return ResponseEntity.badRequest().body(new GeneralResponse<>(GeneralResponse.Status.FAILED,"Student does not exists", null));
        }

        if(!adminStudentService.classExistsById(classId)){
            return ResponseEntity.badRequest().body(new GeneralResponse<>(GeneralResponse.Status.FAILED,"Class does not exists", null));
        }

        if (!adminStudentService.isStudentEnrolledInClass(studentId, classId)) {
            return ResponseEntity.badRequest().body(new GeneralResponse<>(GeneralResponse.Status.FAILED,"This student didn't enroll in this class", null));
        }

        adminStudentService.changeStudentClassStatus(studentId, classId, "pass");
        return ResponseEntity.ok().body(new GeneralResponse<>(GeneralResponse.Status.SUCCESS, "StudentClass set to PASS successfully!", null));
    }

    @PatchMapping("/{student_id}/class/{class_id}/fail")
    public ResponseEntity<?> changeStudentClassStatusToFail(@PathVariable("student_id") int studentId, @PathVariable("class_id") int classId){

        if (!adminStudentService.studentExistsById(studentId)) {
            return ResponseEntity.badRequest().body(new GeneralResponse<>(GeneralResponse.Status.FAILED,"Student does not exists", null));
        }

        if(!adminStudentService.classExistsById(classId)){
            return ResponseEntity.badRequest().body(new GeneralResponse<>(GeneralResponse.Status.FAILED,"Class does not exists", null));
        }

        if (!adminStudentService.isStudentEnrolledInClass(studentId, classId)) {
            return ResponseEntity.badRequest().body(new GeneralResponse<>(GeneralResponse.Status.FAILED,"This student didn't enroll in this class", null));
        }

        adminStudentService.changeStudentClassStatus(studentId, classId, "fail");
        return ResponseEntity.ok().body(new GeneralResponse<>(GeneralResponse.Status.SUCCESS, "StudentClass set to FAIL successfully!", null));
    }
}
