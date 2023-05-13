package com.beaconfire.controller;

import VO.UserSessionVO;
import com.beaconfire.Utils.DayOfWeekUtils;
import com.beaconfire.domain.jdbc.*;
import com.beaconfire.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/class")
public class StudentClassController {


    @Autowired
    private final StudentClassService studentClassService;

    @Autowired
    private final WebRegClassService webRegClassService;

    @Autowired
    private final ClassManagementService classManagementService;

    @Autowired
    private final StudentApplicationService studentApplicationService;


    public StudentClassController(StudentClassService studentClassService, WebRegClassService webRegClassService, ClassManagementService classManagementService, StudentApplicationService studentApplicationService) {
        this.studentClassService = studentClassService;
        this.webRegClassService = webRegClassService;
        this.classManagementService = classManagementService;
        this.studentApplicationService = studentApplicationService;
    }

    @ModelAttribute("userSessionVO")
    public UserSessionVO getUserSessionInfo(HttpSession session){
        if(session.getAttribute("userId") == null){
            return null;
        }
        UserSessionVO userSessionVO = new UserSessionVO();
        userSessionVO.setUserId((Integer) session.getAttribute("userId"));
        userSessionVO.setIs_admin(session.getAttribute("is_admin").toString());
        return userSessionVO;
    }




    @GetMapping("/{class_id}")
    public String getClassDetailByClassId(@PathVariable("class_id") String classId, Model model, HttpSession session){
        if(session.getAttribute("userId") == null){
            return "redirect:/login";
        }
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
        model.addAttribute("webRegClassDisplay", webRegClassDisplay);
        model.addAttribute("classToSemesterDisplay", classToSemesterDisplay);
        model.addAttribute("classToLectureDisplay", classToLectureDisplay);
        model.addAttribute("classToProfessorDisplay", classToProfessorDisplay);
        model.addAttribute("classToClassroomDisplay", classToClassroomDisplay);
        model.addAttribute("classToPrerequisiteDisplay", classToPrerequisiteDisplay);

        if(session.getAttribute("is_admin").equals("1")){
            List<AdminClassToStudentDisplay> studentClassDisplays = studentClassService.getStudentsByClassId(Integer.parseInt(classId));
            model.addAttribute("studentClassDisplays", studentClassDisplays);
        }


        return "classDetails";
    }

    @GetMapping("/all")
    public String getClassManagementByUserId(Model model, HttpSession session){
        if(session.getAttribute("userId") == null){
            return "redirect:/login";
        }
        List<ClassManagementDisplay> classManagementDisplays = classManagementService.getClassManagementDisplay((Integer) session.getAttribute("userId"));
        model.addAttribute("classManagementDisplays", classManagementDisplays);
        return "studentClassManagement";
    }

    @PostMapping("/{class_id}")
    public String sentApplication(@PathVariable("class_id") String classId,
                                  @RequestParam("action") String action,
                                  HttpSession session, Model model){
        if(session.getAttribute("userId") == null){
            return "redirect:/login";
        }
        int studentId = (Integer) session.getAttribute("userId");

        String[] conditions = studentClassService.applicationCheck(studentId, Integer.parseInt(classId));


        String errorMessage = Arrays.stream(conditions)
                .filter(s -> !s.isEmpty())
                .collect(Collectors.joining("<br/>"));

        System.out.println(errorMessage);
        model.addAttribute("errorMessage", errorMessage);


        if(conditions[0].equals("") && "add".equals(action)){//student haven't enrolled
            if((conditions[1] + conditions[2] + conditions[3] + conditions[4] + conditions[6]).equals("")){
                if(conditions[5].equals("")){ // perfectly met condition
                    studentClassService.addStudentToClass(studentId, Integer.parseInt(classId));
                }else{//all good, but class is full
                    studentApplicationService.addNewApplication(studentId, Integer.parseInt(classId), "add");
                }
            }
        }else if("withdraw".equals(action)){// student already enrolled
            if(conditions[7].equals("")){// enrolled within two weeks
                System.out.println("correct position");
                studentApplicationService.removeStudentFromClass(studentId, Integer.parseInt(classId));

            }else{// enrolled more than two weeks
                studentApplicationService.addNewApplication(studentId, Integer.parseInt(classId), "withdraw");
            }
        }
        return getClassManagementByUserId(model, session);
    }


}
