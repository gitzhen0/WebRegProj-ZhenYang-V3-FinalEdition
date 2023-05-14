package com.beaconfire.controller;

import com.beaconfire.domain.DTO.GeneralResponse;
import com.beaconfire.domain.DTO.SignupRequest;
import com.beaconfire.security.JwtUtil;
import com.beaconfire.service.DepartmentService;
import com.beaconfire.service.StudentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;


import javax.validation.Valid;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/signup")
@RequiredArgsConstructor
public class SignupController {

    private final StudentService studentService;
    private final DepartmentService departmentService;

    private final JwtUtil jwtUtil;



    @PostMapping()
    public ResponseEntity<GeneralResponse> processSignup(@Valid @RequestBody SignupRequest sur, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            List<String> errorStrings = new ArrayList<>();
            for(FieldError e: bindingResult.getFieldErrors()){
                errorStrings.add("ValidationError in " + e.getObjectName() + ": " + e.getDefaultMessage());
            }
            return ResponseEntity.badRequest().body(new GeneralResponse<List<String>>(GeneralResponse.Status.FAILED, "RequestBody Field Error", errorStrings));
        }

        Integer id= studentService.registerStudent(sur.getFirst_name(), sur.getLast_name(), sur.getEmail(), sur.getPassword(), sur.getDepartment_id(), 1, 0);

        if (id < 0) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(new GeneralResponse<String>(GeneralResponse.Status.FAILED, "potentially duplicated email", "")); // return conflict status if signup fails
        }

        final UserDetails userDetails = studentService.loadUserByUsername(sur.getEmail());
        final String jwt = jwtUtil.generateToken(userDetails);

        System.out.println("after id");
        URI uri = URI.create(ServletUriComponentsBuilder.fromCurrentContextPath().path("/signup").toUriString());
        return ResponseEntity.created(uri).header("Authorization", "Bearer " + jwt).body(new GeneralResponse(GeneralResponse.Status.SUCCESS, "Signup Success", id));
    }
}
