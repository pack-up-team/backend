package com.swygbro.packup.config;

import java.io.IOException;
import java.time.LocalDateTime;

import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import com.swygbro.packup.user.Mapper.UserMapper;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
@RequiredArgsConstructor
public class CustomAuthenticationSuccessHandler implements AuthenticationSuccessHandler {

    private final UserMapper userMapper;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
            Authentication authentication) throws IOException, ServletException {
        
        String userId = authentication.getName();

        System.out.println("userId : "+userId);
        
        try {
            userMapper.updateLastLoginDate(userId);
            log.info("Updated last login date for user: {}", userId);
        } catch (Exception e) {
            log.error("Failed to update last login date for user: {}", userId, e);
        }
        
        response.sendRedirect("/dashboard/dashboard");
    }
}