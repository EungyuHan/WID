package com.example.wid.configuration;

import com.example.wid.security.JwtFilter;
import com.example.wid.security.JwtUtil;
import com.example.wid.security.LoginFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
    private final AuthenticationConfiguration authenticationConfiguration;
    private final JwtUtil jwtUtil;

    @Autowired
    public SecurityConfig(AuthenticationConfiguration authenticationConfiguration, JwtUtil jwtUtil) {
        this.authenticationConfiguration = authenticationConfiguration;
        this.jwtUtil = jwtUtil;
    }

    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception{
        return configuration.getAuthenticationManager();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.csrf((auth) ->auth.disable());
        http.formLogin((auth) ->auth.disable());
        http.httpBasic((auth) ->auth.disable());
        http.headers((headers) -> headers.frameOptions((options) -> options.disable()));

        http.authorizeHttpRequests((auth) -> auth
                .requestMatchers("/", "/login", "/register/**", "/error").permitAll() // 해당 경로는 모두 허용
                .requestMatchers("/h2-console/**").permitAll() // 해당 경로는 모두 허용

                .requestMatchers("/rsa/**").authenticated() // 해당 경로는 인증된 사용자만 허용

                .requestMatchers("/admin").hasRole("ADMIN") // 해당 경로는 ADMIN 권한만 허용
                .requestMatchers("/user").hasRole("USER") // 해당 경로는 USER 권한만 허용
                .requestMatchers("/issuer").hasRole("ISSUER") // 해당 경로는 ISSUER 권한만 허용
                .requestMatchers("/verifier").hasRole("VERIFIER") // 해당 경로는 VERIFIER 권한만 허용
        );

        http.sessionManagement((session) -> session
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
        );

        http.addFilterBefore(new JwtFilter(jwtUtil), UsernamePasswordAuthenticationFilter.class);
        http.addFilterAt(new LoginFilter(authenticationManager(authenticationConfiguration), jwtUtil), UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }
}
