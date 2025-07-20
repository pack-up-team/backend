package com.swygbro.packup.login.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class LoginController {

    @GetMapping("/login")
    public String login() {
        System.out.println("로그인 이곳은 들어오냐??");
        return "login/login";
    }

    @GetMapping("/loginTest")
    public String loginTest() {
        System.out.println("로그인 이곳은 들어오냐1111??");
        return "login";
    }

}