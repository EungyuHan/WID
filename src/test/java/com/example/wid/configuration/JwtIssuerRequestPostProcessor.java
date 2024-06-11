package com.example.wid.configuration;

import com.example.wid.security.JwtUtil;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.test.web.servlet.request.RequestPostProcessor;

public class JwtIssuerRequestPostProcessor implements RequestPostProcessor {
    private final JwtUtil jwtUtil;
    private final String username;
    private final String role;

    public JwtIssuerRequestPostProcessor(JwtUtil jwtUtil, String username, String role) {
        this.jwtUtil = jwtUtil;
        this.username = username;
        this.role = role;
    }
    public static JwtIssuerRequestPostProcessor jwtIssuer(JwtUtil jwtUtil, String username, String role) {
        return new JwtIssuerRequestPostProcessor(jwtUtil, username, role);
    }
    @Override
    public MockHttpServletRequest postProcessRequest(MockHttpServletRequest request) {
        String token = jwtUtil.createJwtToken(username, role);
        request.addHeader("Authorization", "Bearer " + token);
        return request;
    }
}
