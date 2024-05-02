package com.example.wid.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.FilterChain;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.io.IOException;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class LoginFilter extends UsernamePasswordAuthenticationFilter {
    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;
    private final Long expiredMs;

    public LoginFilter(AuthenticationManager authenticationManager, JwtUtil jwtUtil) {
        this.authenticationManager = authenticationManager;
        this.jwtUtil = jwtUtil;
        this.expiredMs = 1000L * 60 * 60 * 24 * 7; // 7일
    }

    // 로그인 시도시 실행하는 메소드
    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {

        //클라이언트 요청에서 username, password 추출
        String username = obtainUsername(request);
        String password = obtainPassword(request);

        //스프링 시큐리티에서 username과 password를 검증하기 위해서는 token에 담아야 함
        UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(username, password, null);

        //token에 담은 검증을 위한 AuthenticationManager로 전달
        return authenticationManager.authenticate(authToken);
    }

    //로그인 성공시 실행하는 메소드 (여기서 JWT를 발급하면 됨)
    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authentication)
            throws IOException {
        // authentication.getPrincipal -> 특정한 유저 확인 가능
        // 받아온 User를 customUserDetails에 저장
        CustomUserDetails customUserDetails = (CustomUserDetails) authentication.getPrincipal();
        String username = customUserDetails.getUsername(); // username 불러오기
        // 권한 불러옴, 1개만 저장
        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
        Iterator<? extends GrantedAuthority> iterator = authorities.iterator();
        GrantedAuthority auth = iterator.next();

        String role = auth.getAuthority();

        String token = jwtUtil.createJwtToken(username, role, expiredMs);

        // 응답의 Content-Type을 application/json으로 설정
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        // 토큰 정보를 포함하는 객체 생성
        Map<String, String> tokenInfo = new HashMap<>();
        tokenInfo.put("token", token);

        // Jackson 라이브러리를 사용하여 tokenInfo 객체를 JSON 문자열로 변환
        String tokenJson = new ObjectMapper().writeValueAsString(tokenInfo);

        // 응답 바디에 JSON 문자열 쓰기
        response.getWriter().write(tokenJson);
        response.getWriter().flush();
    }

    //로그인 실패시 실행하는 메소드
    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response, AuthenticationException failed) {
        // 응답의 Content-Type을 application/json으로 설정
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        // 실패 메시지를 포함하는 객체 생성
        Map<String, String> error = new HashMap<>();
        error.put("error", "로그인에 실패하였습니다.");

        // Jackson 라이브러리를 사용하여 error 객체를 JSON 문자열로 변환
        try {
            String errorJson = new ObjectMapper().writeValueAsString(error);

            // 응답 바디에 JSON 문자열 쓰기
            response.getWriter().write(errorJson);
            response.getWriter().flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}