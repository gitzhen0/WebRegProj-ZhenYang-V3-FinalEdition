package com.beaconfire.controller;

import com.beaconfire.Utils.AdminApplicationUtil;
import com.beaconfire.domain.DTO.AdminManageApplicationRequest;
import com.beaconfire.domain.DTO.GeneralResponse;
import com.beaconfire.domain.jdbc.AdminApplication;
import com.beaconfire.domain.jdbc.SingleApplication;
import com.beaconfire.service.StudentApplicationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;

import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/admin/application")
public class AdminApplicationController {

    @Autowired
    StudentApplicationService studentApplicationService;


    @GetMapping("/all")
    public ResponseEntity<?> getAllApplication() {

        List<AdminApplication> adminApplicationList = studentApplicationService.getAllApplications();
        AdminApplicationUtil.sortApplicationsByCreationTimeDescending(adminApplicationList);

        adminApplicationList.stream().forEach(aal -> {
            aal.setFull_name(aal.getFirst_name() + " " + aal.getLast_name());
            aal.setApplication_id(null);
            aal.setFirst_name(null);
            aal.setLast_name(null);
            aal.setClass_id(null);
            aal.setStudent_id(null);

        });

        return ResponseEntity.ok().body(new GeneralResponse<List<AdminApplication>>(GeneralResponse.Status.SUCCESS, "find all application success", adminApplicationList));
    }

    @PatchMapping("/{application_id}")
    public ResponseEntity<?> changeApplicationStatus(
            @PathVariable("application_id") int applicationId,
            @RequestBody AdminManageApplicationRequest inputBody) {

        if (!studentApplicationService.applicationExistsById(applicationId)) {
            return ResponseEntity.badRequest().body(new GeneralResponse<>(GeneralResponse.Status.FAILED, "application id doesn't exists", null));
        }

        if(!studentApplicationService.applicationIsPendingById(applicationId)){

            return ResponseEntity.badRequest().body(new GeneralResponse<>(GeneralResponse.Status.FAILED, "application status is not pending", null));
        }

        studentApplicationService.updateApplicationStatus(applicationId, inputBody.getOperation(), inputBody.getFeedback());

        if (inputBody.getOperation().equals("approve")) {
            studentApplicationService.processApprovedApplication(applicationId);
        }

        return ResponseEntity.ok().body(new GeneralResponse<SingleApplication>(GeneralResponse.Status.SUCCESS, "Application Updated Successfully", studentApplicationService.getApplicationById(applicationId)));
    }
}
