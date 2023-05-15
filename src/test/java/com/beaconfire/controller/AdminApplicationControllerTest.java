package com.beaconfire.controller;

import com.beaconfire.config.SecurityConfig;
import com.beaconfire.domain.DTO.GeneralResponse;
import com.beaconfire.domain.hibernate.StudentHibernate;
import com.beaconfire.domain.jdbc.AdminApplication;


import com.beaconfire.security.JwtRequestFilter;
import com.beaconfire.security.JwtUtil;
import com.beaconfire.service.StudentApplicationService;
import com.beaconfire.service.StudentService;
import org.junit.Before;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
//import org.junit.Test;
import org.junit.runner.RunWith;
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

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;


@WebMvcTest(AdminApplicationController.class)
@Import({SecurityConfig.class, JwtRequestFilter.class, JwtUtil.class})
@AutoConfigureMockMvc(addFilters = false)
public class AdminApplicationControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private StudentApplicationService studentApplicationService;

    @Autowired
    private AdminApplicationController adminApplicationController;
    @MockBean
    private StudentService studentService;

    @Autowired
    private JwtUtil jwtUtil;

    @BeforeEach
    public void setup() {

        // Assume that the studentService.loadUserByUsername() method returns a valid UserDetails object
        UserDetails userDetails = new User("admin", "password", AuthorityUtils.createAuthorityList("ADMIN"));

        Mockito.when(studentService.loadUserByUsername("admin")).thenReturn(userDetails);

        // Assume that the studentService.getStudentByEmail2() method returns a valid StudentHibernate object
        StudentHibernate student = new StudentHibernate();
        student.setId(1);

        System.out.println(student.toString());
        Mockito.when(studentService.getStudentByEmail2("admin")).thenReturn(student);

    }
    @Test
    @WithMockUser(value = "admin", roles = {"ADMIN"})
    public void getAllApplication() throws Exception {
        List<AdminApplication> expected = new ArrayList<>();

        AdminApplication dummy = AdminApplication.builder()
                .first_name("John")
                .last_name("Doe")
                .build();
        expected.add(dummy);
        Mockito.when(studentApplicationService.getAllApplications()).thenReturn(expected);
        assertEquals(ResponseEntity.ok().body(new GeneralResponse<List<AdminApplication>>(GeneralResponse.Status.SUCCESS, "find all application success", expected))
                ,adminApplicationController.getAllApplication());
        String token = jwtUtil.generateToken(studentService.loadUserByUsername("admin"));
//        String token = jwtUtil.generateToken(new User("admin", "password", new ArrayList<>()));
        mockMvc.perform(MockMvcRequestBuilders.get("/admin/application/all")
                        .header("Authorization", "Bearer " + token))
                .andExpect(MockMvcResultMatchers.status().isOk()) // status code 200
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.content().json(
                        "{\n" +
                                "    \"status\": \"SUCCESS\",\n" +
                                "    \"message\": \"find all application success\",\n" +
                                "    \"body\": [\n" +
                                "        {\n" +


                                "            \"full_name\": \"null null\"\n" +


                                "        }\n" +
                                "    ]\n" +
                                "}"));

    }

//    @Test
//    void changeApplicationStatus() {
//    }
}