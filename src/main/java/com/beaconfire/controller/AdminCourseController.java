package com.beaconfire.controller;


import com.beaconfire.domain.jdbc.*;
import com.beaconfire.service.AdminCourseService;
import com.beaconfire.service.DepartmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
@RequestMapping("/admin/course")
public class AdminCourseController {

    @Autowired
    AdminCourseService adminCourseService;

    @Autowired
    DepartmentService departmentService;

    @ModelAttribute("departmentList")
    public List<Department> getDepartmentList(){
        List<Department> departmentList = departmentService.getAllDepartments();
        return departmentList;
    }

    //lazy...

    @ModelAttribute("courseList")
    public List<AdminCourseDisplay> getCourseList(){
        List<AdminCourseDisplay> courseList = adminCourseService.getAllCourses();
        return courseList;
    }

    @ModelAttribute("professorList")
    public List<AdminProfessor> getProfessorList(){
        List<AdminProfessor> professorList = adminCourseService.getAllProfessors();

        return professorList;
    }

    @ModelAttribute("semesterList")
    public List<AdminSemester> getSemesterList(){
        List<AdminSemester> semesterList = adminCourseService.getAllSemesters();
        return semesterList;
    }

    @ModelAttribute("classroomList")
    public List<AdminClassroom> getClassroomList(){
        List<AdminClassroom> classroomList = adminCourseService.getAllClassrooms();
        return classroomList;
    }


    @GetMapping("/all")
    public String getAllCourse(HttpSession session, Model model){
        if(session.getAttribute("userId") == null || !session.getAttribute("is_admin").equals("1")){
            return "redirect:/login";
        }
        List<AdminCourseDisplay> adminCourseDisplayList = adminCourseService.getAdminCourseDisplays();
        model.addAttribute("adminCourseDisplayList", adminCourseDisplayList);
        return "adminCourse";
    }

    @PostMapping()
    public String addNewCourse(@RequestParam("course_name") String course_name,
                               @RequestParam("course_code") String course_code,
                               @RequestParam("description") String description,
                               @RequestParam("department_id") int department_id,
                               HttpSession session,
                               Model model){
        if(session.getAttribute("userId") == null || !session.getAttribute("is_admin").equals("1")){
            return "redirect:/login";
        }
        adminCourseService.addNewCourse(course_name, course_code, department_id, description);
        return "redirect:/admin/course/all";
    }



}
