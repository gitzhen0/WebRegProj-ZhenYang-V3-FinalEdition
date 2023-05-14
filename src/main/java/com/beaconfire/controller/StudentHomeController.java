package com.beaconfire.controller;

import com.beaconfire.domain.DTO.GeneralResponse;
import com.beaconfire.domain.jdbc.StudentClassDisplay;
import com.beaconfire.security.JwtUtil;
import com.beaconfire.service.StudentClassService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
@RequestMapping()
@RequiredArgsConstructor
public class StudentHomeController {

    @Autowired
    private final StudentClassService studentClassService;

    @Autowired
    private final JwtUtil jwtUtil;



    @GetMapping("/class/all/{page}/{limit}")
    public ResponseEntity<?> getStudentClasses(
            @PathVariable("page") int page,
            @PathVariable("limit") int limit,
            HttpServletRequest request) {

        int id = jwtUtil.extractId(request.getHeader("Authorization").substring(7));
        List<StudentClassDisplay> studentClassDisplays = studentClassService.displayStudentClass(page, limit, id);

        studentClassDisplays.stream().forEach(scd -> {
            scd.setClass_id(null);
            scd.setStudent_id(null);
        });


        return ResponseEntity.ok().body(new GeneralResponse<List<StudentClassDisplay>>(GeneralResponse.Status.SUCCESS, "success message", studentClassDisplays));
    }
}
