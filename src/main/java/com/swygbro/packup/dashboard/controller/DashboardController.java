package com.swygbro.packup.dashboard.controller;

import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import lombok.extern.slf4j.Slf4j;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Slf4j
@RestController
@RequestMapping("/dashboard")
public class DashboardController {

    @GetMapping("/dashboard")
    public ModelAndView dashboard(Authentication authentication) {
        // 인증 정보가 없거나 인증되지 않은 경우 main.jsp로 리다이렉트
        if (authentication == null || !authentication.isAuthenticated() || 
            "anonymousUser".equals(authentication.getName())) {
            log.info("Unauthorized access to dashboard, redirecting to main");
            return new ModelAndView("redirect:/");
        }
        
        log.info("Dashboard accessed by user: {}", authentication.getName());
        
        ModelAndView mv = new ModelAndView("dashboard/dashboard");
        mv.addObject("username", authentication.getName());
        mv.addObject("currentTime", LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
        
        // 이미지 참조번호 추가 (실제로는 DB에서 가져와야 함)
        mv.addObject("imageRefNo", 1);
        mv.addObject("fileCate1", "object");
        mv.addObject("fileCate2", "default");
        
        return mv;
    }
}