package com.swygbro.packup.login.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.swygbro.packup.user.service.UserService;
import com.swygbro.packup.user.vo.UserVo;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/lgn")
public class LoginController {

    private final UserService userService;

    @GetMapping("/login")
    public String login() {
        return "login/login";
    }

    @GetMapping("/register")
    public String register() {
        return "login/register";
    }

    @GetMapping("/successLogin")
    public String successLogin() {
        return "login/successLogin";
    }

    @GetMapping("/faliureLogin")
    public String faliureLogin() {
        return "login/loginError";
    }

    @PostMapping("/insertUser")
    public String insertUser(UserVo userDto) {
        log.info("insertUser ::: {}",userDto);
        int res = userService.insertUser(userDto);
        log.info("res ::: {}",res);

        if(res==1){
            return "redirect:/index";
        }else{
            return "redirect:/error/registerError";
        }
    }

}