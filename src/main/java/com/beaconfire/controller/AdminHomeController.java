package com.beaconfire.controller;

import com.beaconfire.domain.DTO.GeneralResponse;
import com.beaconfire.domain.jdbc.AdminHomeDisplay;
import com.beaconfire.service.AdminHomeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
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

        List<AdminHomeDisplay> result = adminHomeService.displayAdminHomeStudents(page, limit);


        return ResponseEntity.ok().body(new GeneralResponse<List<AdminHomeDisplay>>(GeneralResponse.Status.SUCCESS, "find all student success", result));
    }


}
