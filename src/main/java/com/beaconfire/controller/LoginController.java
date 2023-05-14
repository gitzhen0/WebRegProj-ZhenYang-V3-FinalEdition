package com.beaconfire.controller;

import com.beaconfire.domain.DTO.AuthenticationRequest;
import com.beaconfire.domain.DTO.AuthenticationResponse;
import com.beaconfire.domain.DTO.GeneralResponse;
import com.beaconfire.security.JwtUtil;
import com.beaconfire.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/login")
public class LoginController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private StudentService studentService;


    @PostMapping
    public ResponseEntity<?> createAuthenticationToken(@Valid @RequestBody AuthenticationRequest authenticationRequest, BindingResult bindingResult) throws Exception {

        if (bindingResult.hasErrors()) {
            List<String> errorStrings = new ArrayList<>();
            for(FieldError e: bindingResult.getFieldErrors()){
                errorStrings.add("ValidationError in " + e.getObjectName() + ": " + e.getDefaultMessage());
            }
            return ResponseEntity.badRequest().body(new GeneralResponse<List<String>>(GeneralResponse.Status.FAILED, "RequestBody Field Error", errorStrings));
        }


        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authenticationRequest.getEmail(), authenticationRequest.getPassword()));
        } catch (BadCredentialsException e) {
            throw new Exception("Incorrect username or password", e);
        }

        final UserDetails userDetails = studentService.loadUserByUsername(authenticationRequest.getEmail());

        final String jwt = jwtUtil.generateToken(userDetails);

        return ResponseEntity.ok().header("Authorization", "Bearer " + jwt).body(new AuthenticationResponse(jwt, "login success", "SUCCESS")); // Add token to header
    }


}
