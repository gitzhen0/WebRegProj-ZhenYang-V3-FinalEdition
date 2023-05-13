package com.beaconfire.controller;

import com.beaconfire.service.AdminHomeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("/admin/home")
public class AdminHomeController {

    @Autowired
    private final AdminHomeService adminHomeService;

    public AdminHomeController(AdminHomeService adminHomeService) {
        this.adminHomeService = adminHomeService;
    }

    @GetMapping()
    public String getAdminHome(
            @RequestParam(required = false, defaultValue = "1") int page,
            @RequestParam(required = false, defaultValue = "4") int limit,
            HttpSession session,
            Model model) {
        if (session.getAttribute("userId") == null || !session.getAttribute("is_admin").equals("1")) {
            return "redirect:/login";
        }

        return adminHomeService.displayAdminHomeStudents(page, limit, model);
    }


}
