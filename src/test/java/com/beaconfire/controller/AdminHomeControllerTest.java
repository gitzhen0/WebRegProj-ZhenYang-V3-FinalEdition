package com.beaconfire.controller;

import com.beaconfire.config.SecurityConfig;
import com.beaconfire.domain.DTO.GeneralResponse;
import com.beaconfire.domain.hibernate.StudentHibernate;
import com.beaconfire.domain.jdbc.AdminHomeDisplay;
import com.beaconfire.domain.jdbc.Student;
import com.beaconfire.security.JwtRequestFilter;
import com.beaconfire.security.JwtUtil;
import com.beaconfire.service.AdminHomeService;
import com.beaconfire.service.StudentService;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import org.mockito.Mockito;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.Mockito.when;





@WebMvcTest(AdminHomeController.class)
@Import({SecurityConfig.class, JwtRequestFilter.class, JwtUtil.class})
@AutoConfigureMockMvc(addFilters = false)
public class AdminHomeControllerTest {


    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private JwtUtil jwtUtil;

    @MockBean
    private StudentService studentService;

    @MockBean
    AdminHomeService adminHomeService;
    //

    @BeforeEach
    public void setup() {
        System.out.println("start1AaaaAaAAAAAA");
        // Assume that the studentService.loadUserByUsername() method returns a valid UserDetails object
        UserDetails userDetails = new User("admin", "password", AuthorityUtils.createAuthorityList("ADMIN"));
        Mockito.when(studentService.loadUserByUsername("admin")).thenReturn(userDetails);

        // Assume that the studentService.getStudentByEmail2() method returns a valid StudentHibernate object
        StudentHibernate student = new StudentHibernate();
        student.setId(1);

        Mockito.when(studentService.getStudentByEmail2("admin")).thenReturn(student);
        System.out.println("start1BBBBBBBBBBBBB");
    }


    @Test
    @WithMockUser(value = "admin", roles = {"ADMIN"})
    public void getAdminHome() throws Exception {
        List<AdminHomeDisplay> expected = new ArrayList<>();

        AdminHomeDisplay tmp = AdminHomeDisplay.builder()
                .email("admin@1")
                .department_name("testDepartmentName")
                .full_name("yang zhen")
                .is_active("True")
                .school_name("java school")
                .student_id(0)
                .first_name("yang")
                .last_name("zhen")
                .build();

        expected.add(tmp);
        when(adminHomeService.displayAdminHomeStudents(1,1)).thenReturn(expected);

        ResponseEntity<GeneralResponse> tmp2 = ResponseEntity.ok().body(new GeneralResponse<>(GeneralResponse.Status.SUCCESS, "find all student success", expected));
        AdminHomeController adminHomeController = new AdminHomeController(adminHomeService);

        assertEquals(tmp2, adminHomeController.getAdminHome(1,1));



//        String token = jwtUtil.generateToken(studentService.loadUserByUsername("admin"));
        String token = jwtUtil.generateToken(new User("admin", "password", new ArrayList<>()));
        System.out.println("test token: " + token);
        mockMvc.perform(MockMvcRequestBuilders.get("/admin/student/all/1/1")
                        .header("Authorization", "Bearer " + token))
                .andExpect(MockMvcResultMatchers.status().isOk()) // status code 200
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.content().json(
                        "{\n" +
                                "    \"status\": \"SUCCESS\",\n" +
                                "    \"message\": \"find all student success\",\n" +
                                "    \"body\": [\n" +
                                "        {\n" +
                                "            \"student_id\": 0,\n" +
                                "            \"first_name\": \"yang\",\n" +
                                "            \"last_name\": \"zhen\",\n" +
                                "            \"full_name\": \"yang zhen\",\n" +
                                "            \"email\": \"admin@1\",\n" +
                                "            \"department_name\": \"testDepartmentName\",\n" +
                                "            \"school_name\": \"java school\",\n" +
                                "            \"is_active\": \"True\"\n" +
                                "        }\n" +
                                "    ]\n" +
                                "}"));

    }
}
