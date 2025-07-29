package com.swygbro.packup.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private final CustomAuthenticationSuccessHandler customAuthenticationSuccessHandler;

    public SecurityConfig(CustomAuthenticationSuccessHandler customAuthenticationSuccessHandler) {
        this.customAuthenticationSuccessHandler = customAuthenticationSuccessHandler;
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        return http
                .cors(cors -> cors.disable())
                .csrf(csrf -> csrf.disable())
                
                .authorizeHttpRequests((auth) -> auth
                        .requestMatchers("/swagger-ui/**", "/v3/api-docs/**", "/swagger-ui.html", "/webjars/**").permitAll()
                        .requestMatchers("/admin/**").hasRole("ADMIN")
                        .requestMatchers("/lgn/*","/register/*","/register","/lgn").permitAll()     // lgn* → login*
                        .requestMatchers("/test*").permitAll()
                        .requestMatchers("/sample/**").permitAll()
                        .requestMatchers("/component/**").permitAll()
                        .requestMatchers("/auth/**").permitAll()
                        .requestMatchers("/temp/**").permitAll()
                        .requestMatchers("/").permitAll()  // 루트 경로 허용
                        .requestMatchers("/mypage/**").hasAnyRole("ADMIN", "USER")
                        .requestMatchers("/**").hasAnyRole("ADMIN", "USER")
                )
                
                .formLogin((auth) -> auth
                        .loginPage("/login/login")                        // lgn → login
                        .loginProcessingUrl("/loginProcess")        // lgn/lgn → loginProcess
                        .usernameParameter("username")              // mngrId → username
                        .passwordParameter("password")              // pswd → password
                        .successHandler(customAuthenticationSuccessHandler)
                        .failureUrl("/login/login?error=true")
                        .permitAll()
                )
                
                .logout(logout -> logout
                        .logoutUrl("/logout")
                        .logoutSuccessUrl("/login/login?logout=true")
                        .invalidateHttpSession(true)
                        .deleteCookies("JSESSIONID")
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
    
    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

}