package com.beaconfire.security;

import com.beaconfire.domain.DTO.GeneralResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class JwtAuthenticationEntryPoint implements AuthenticationEntryPoint {

    private final ObjectMapper objectMapper;

    @Autowired
    public JwtAuthenticationEntryPoint(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response,
                         AuthenticationException authException) throws IOException {

        response.setContentType("application/json");
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);

        GeneralResponse<String> generalResponse = new GeneralResponse<>();
        generalResponse.setStatus(GeneralResponse.Status.FAILED);
        generalResponse.setMessage(authException.getMessage());

        String jsonResponse = objectMapper.writeValueAsString(generalResponse);
        response.getOutputStream().println(jsonResponse);
    }
}

