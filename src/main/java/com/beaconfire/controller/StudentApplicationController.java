package com.beaconfire.controller;

import com.beaconfire.domain.DTO.GeneralResponse;
import com.beaconfire.domain.jdbc.StudentApplicationDisplay;
import com.beaconfire.security.JwtUtil;
import com.beaconfire.service.StudentApplicationService;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
@RequestMapping("/application")
@RequiredArgsConstructor
public class StudentApplicationController {

    @Autowired
    private final JwtUtil jwtUtil;

    @Autowired
    private final StudentApplicationService studentApplicationService;



    @GetMapping("/all")
    public ResponseEntity<?> getStudentApplicationById(HttpServletRequest request){

        int studentId = jwtUtil.extractId(request.getHeader("Authorization").substring(7));

        List<StudentApplicationDisplay> studentApplicationDisplays = studentApplicationService.getStudentApplicationDisplay(studentId);

        //i dont' need those field in my json response, so i set them to null to hide them in json response
        //also need add annotation in the class
        studentApplicationDisplays.stream().forEach(sad -> {
            sad.setApplication_id(null);
            sad.setStudent_id(null);
            sad.setClass_id(null);
        });

       return ResponseEntity.ok().body(new GeneralResponse<List<StudentApplicationDisplay>>(GeneralResponse.Status.SUCCESS, "find application success", studentApplicationDisplays));
    }

    @DeleteMapping("/{application_id}")
    public ResponseEntity<?> removeApplication(@PathVariable("application_id") int applicationId, HttpServletRequest request){

        if (!studentApplicationService.applicationExistsById(applicationId)) {
            return ResponseEntity.badRequest().body(new GeneralResponse<>(GeneralResponse.Status.FAILED, "application id doesn't exists", null));
        }

        int studentId = jwtUtil.extractId(request.getHeader("Authorization").substring(7));

        String[] message = studentApplicationService.validToRemove(studentId, applicationId);

        if(message[0].equals("") && message[1].equals("") && message[2].equals("")){
            studentApplicationService.removeApplicationById(applicationId);
            return ResponseEntity.ok().body(new GeneralResponse<>(GeneralResponse.Status.SUCCESS, "All condition is met, remove success", null));
        }else{
            return ResponseEntity.badRequest().body(new GeneralResponse<String>(GeneralResponse.Status.FAILED, "remove failed", String.join(", ", message)));
        }
    }
}
