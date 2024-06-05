package com.example.wid.configuration;

import com.example.wid.security.JwtUtil;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.test.web.servlet.request.RequestPostProcessor;

public class JwtUserRequestPostProcessor implements RequestPostProcessor {
    private final JwtUtil jwtUtil;
    private final String username;
    private final String role;

    public JwtUserRequestPostProcessor(JwtUtil jwtUtil, String username, String role) {
        this.jwtUtil = jwtUtil;
        this.username = username;
        this.role = role;
    }
    public static JwtUserRequestPostProcessor jwtUser(JwtUtil jwtUtil, String username, String role) {
        return new JwtUserRequestPostProcessor(jwtUtil, username, role);
    }
    @Override
    public MockHttpServletRequest postProcessRequest(MockHttpServletRequest request) {
        String token = jwtUtil.createJwtToken(username, role);
        request.addHeader("Authorization", "Bearer " + token);
        return request;
    }
}
