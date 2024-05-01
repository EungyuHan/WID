package com.example.wid.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.authorizeHttpRequests((auth) -> auth
                .requestMatchers("/", "/login", "/register/**").permitAll() // 해당 경로는 모두 허용
                .requestMatchers(("/admin")).hasRole("ADMIN") // 해당 경로는 ADMIN 권한만 허용
                .anyRequest().authenticated() // 위에서 명시하지 않은 나머지 경로는 로그인 해야만 허용
        );

        http.csrf((auth) ->
                auth.disable()
        );

        return http.build();
    }
}
