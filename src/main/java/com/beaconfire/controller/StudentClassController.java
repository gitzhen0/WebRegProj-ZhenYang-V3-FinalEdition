package com.beaconfire.controller;

import com.beaconfire.Utils.DayOfWeekUtils;
import com.beaconfire.domain.DTO.GeneralResponse;
import com.beaconfire.domain.DTO.StudentGetClassResponse;
import com.beaconfire.domain.jdbc.*;
import com.beaconfire.security.JwtUtil;
import com.beaconfire.service.*;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/class")
@RequiredArgsConstructor
public class StudentClassController {

    @Autowired
    private final StudentService studentService;


    @Autowired
    private final StudentClassService studentClassService;

    @Autowired
    private final WebRegClassService webRegClassService;

    @Autowired
    private final ClassManagementService classManagementService;

    @Autowired
    private final StudentApplicationService studentApplicationService;

    @Autowired
    private final JwtUtil jwtUtil;


    @GetMapping("/{class_id}")
    public ResponseEntity<?> getClassDetailByClassId(@PathVariable("class_id") String classId, HttpServletRequest request){

        WebRegClassDisplay webRegClassDisplay = webRegClassService.getWebRegClassDisplayByClassId(Integer.parseInt(classId));
        ClassToSemesterDisplay classToSemesterDisplay = webRegClassService.getClassToSemesterDisplayByClassId(Integer.parseInt(classId));
        ClassToLectureDisplay classToLectureDisplay = webRegClassService.getClassToLectureDisplayByClassId(Integer.parseInt(classId));
        ClassToProfessorDisplay classToProfessorDisplay = webRegClassService.getClassToProfessorDisplayByClassId(Integer.parseInt(classId));
        ClassToClassroomDisplay classToClassroomDisplay = webRegClassService.getClassToClassroomDisplayByClassId(Integer.parseInt(classId));
        ClassToPrerequisiteDisplay classToPrerequisiteDisplay = webRegClassService.getClassToPrerequisiteDisplayByClassId(Integer.parseInt(classId));

        if(Integer.parseInt(webRegClassDisplay.getIs_active()) > 0){
            webRegClassDisplay.setIs_active("Active");
        }else{
            webRegClassDisplay.setIs_active("Inactive");
        }

        classToLectureDisplay.setDay_of_the_week(DayOfWeekUtils.getDayOfWeek(classToLectureDisplay.getDay_of_the_week()));

        List<AdminClassToStudentDisplay> studentClassDisplays = studentClassService.getStudentsByClassId(Integer.parseInt(classId));

        StudentGetClassResponse studentGetClassResponse = StudentGetClassResponse.builder()
                .webRegClass(webRegClassDisplay)
                .semester(classToSemesterDisplay)
                .professor(classToProfessorDisplay)
                .lecture(classToLectureDisplay)
                .classroom(classToClassroomDisplay)
                .prerequisites(classToPrerequisiteDisplay)
                .students(studentClassDisplays)
                .build();

        // check if this user have the ADMIN authority
        String jwt = request.getHeader("Authorization").substring(7);
        UserDetails userDetails = studentService.loadUserByUsername(jwtUtil.extractUsername(jwt));
        Collection<? extends GrantedAuthority> authorities = userDetails.getAuthorities();
        boolean isAdmin = authorities.contains(new SimpleGrantedAuthority("ADMIN"));
        if(!isAdmin){
            studentGetClassResponse.setStudents(null);
        }

        return ResponseEntity.ok().body(new GeneralResponse<StudentGetClassResponse>(GeneralResponse.Status.SUCCESS, "FOUND CLASS SUCCESSFULLY", studentGetClassResponse));
    }

    @GetMapping("/all")
    public ResponseEntity<?> getClassManagementByUserId(HttpServletRequest request){

        int id = jwtUtil.extractId(request.getHeader("Authorization").substring(7));
        List<ClassManagementDisplay> classManagementDisplays = classManagementService.getClassManagementDisplay(id);
        return ResponseEntity.ok().body(new GeneralResponse<List<ClassManagementDisplay>>(GeneralResponse.Status.SUCCESS, "FOUND THOSE ACTIVE CLASSES",classManagementDisplays));
    }

}
