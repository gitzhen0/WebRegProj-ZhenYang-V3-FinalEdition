package com.beaconfire.controller;


import com.beaconfire.domain.jdbc.AdminClassDisplay;
import com.beaconfire.service.AdminClassDisplayService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.time.LocalTime;
import java.util.List;

@RestController
public class TrialController {

    @GetMapping("/admin/test1")
    public ResponseEntity<String> get1(){
        return ResponseEntity.ok().body("/admin/test1");
    }

    @GetMapping("/test1")
    public ResponseEntity<String> get2(){
        return ResponseEntity.ok().body("/test1");
    }

    @GetMapping("/admin/test2")
    public ResponseEntity<String> get3(){
        return ResponseEntity.ok().body("/admin/test2");
    }

    @GetMapping("/test2")
    public ResponseEntity<String> get4(){
        return ResponseEntity.ok().body("/test2");
    }


}
