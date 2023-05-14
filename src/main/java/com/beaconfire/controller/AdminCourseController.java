package com.beaconfire.controller;


import com.beaconfire.domain.DTO.AdminAddCourse;
import com.beaconfire.domain.DTO.GeneralResponse;
import com.beaconfire.domain.jdbc.*;
import com.beaconfire.service.AdminCourseService;
import com.beaconfire.service.DepartmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
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
    public ResponseEntity<?> getAllCourse(){
        List<AdminCourseDisplay> adminCourseDisplayList = adminCourseService.getAdminCourseDisplays();

        adminCourseDisplayList.stream().forEach(acdl -> {
            acdl.setCourse_id(null);
        });

        return ResponseEntity.ok().body(new GeneralResponse<List<AdminCourseDisplay>>(GeneralResponse.Status.SUCCESS, "success message", adminCourseDisplayList));
    }

    @PostMapping()
    public ResponseEntity<?> addNewCourse(@RequestBody AdminAddCourse input){

        if(!departmentService.departmentExistsById(input.getDepartment_id())){
            return ResponseEntity.badRequest().body(new GeneralResponse<>(GeneralResponse.Status.FAILED, "Department does not exists", null));
        }

        adminCourseService.addNewCourse(input.getCourse_name(), input.getCourse_code(), input.getDepartment_id(), input.getDescription());
        return ResponseEntity.ok().body(new GeneralResponse<AdminAddCourse>(GeneralResponse.Status.SUCCESS, "Add new course successfully", input));
    }



}
