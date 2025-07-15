package com.swygbro.packup.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        return http
                .cors(cors -> cors.disable())
                .csrf(csrf -> csrf.disable())
                .headers(headers -> headers.frameOptions().sameOrigin())
                
                .authorizeHttpRequests((auth) -> auth
                        .requestMatchers("/admin/**").hasRole("ADMIN")
                        .requestMatchers("/login*").permitAll()     // lgn* → login*
                        .requestMatchers("/test*").permitAll()
                        .requestMatchers("/sample/**").permitAll()
                        .requestMatchers("/component/**").permitAll()
                        .requestMatchers("/**").hasAnyRole("ADMIN", "USER")
                )
                
                .formLogin((auth) -> auth
                        .loginPage("/login")                        // lgn → login
                        .loginProcessingUrl("/loginProcess")        // lgn/lgn → loginProcess
                        .usernameParameter("username")              // mngrId → username
                        .passwordParameter("password")              // pswd → password
                        .permitAll()
                )
                
                .sessionManagement(session -> session
                        .maximumSessions(1)
                        .maxSessionsPreventsLogin(false)
                )
                
                .build();
    }

    // ✅ 정적 리소스 완전 제외 (핵심!)
    @Bean
    public WebSecurityCustomizer webSecurityCustomizer() {
        return (web) -> web.ignoring()
                .requestMatchers("/css/**", "/js/**", "/images/**", "/WEB-INF/**");
    }

}