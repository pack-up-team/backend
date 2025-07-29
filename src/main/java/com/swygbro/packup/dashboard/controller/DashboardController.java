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
        log.info("Dashboard accessed by user: {}", authentication.getName());
        
        ModelAndView mv = new ModelAndView("dashboard/dashboard");
        mv.addObject("username", authentication.getName());
        mv.addObject("currentTime", LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
        
        return mv;
    }
}