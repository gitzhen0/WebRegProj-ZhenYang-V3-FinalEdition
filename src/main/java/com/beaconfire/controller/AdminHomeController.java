package com.beaconfire.controller;

import com.beaconfire.domain.DTO.GeneralResponse;
import com.beaconfire.domain.jdbc.AdminHomeDisplay;
import com.beaconfire.service.AdminHomeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/admin")
public class AdminHomeController {

    @Autowired
    private final AdminHomeService adminHomeService;

    public AdminHomeController(AdminHomeService adminHomeService) {
        this.adminHomeService = adminHomeService;
    }

    @GetMapping("/student/all/{page}/{limit}")
    public ResponseEntity<?> getAdminHome(
            @PathVariable("page") int page,
            @PathVariable("limit") int limit) {

        if(page<=0 || limit<=0){
            throw new NumberFormatException("page and limit must be greater than 0");
        }

        List<AdminHomeDisplay> result = adminHomeService.displayAdminHomeStudents(page, limit);


        return ResponseEntity.ok().body(new GeneralResponse<List<AdminHomeDisplay>>(GeneralResponse.Status.SUCCESS, "find all student success", result));
    }


}
