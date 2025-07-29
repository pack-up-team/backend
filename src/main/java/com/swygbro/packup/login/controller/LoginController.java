package com.swygbro.packup.login.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

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
    public ModelAndView login() {
        return new ModelAndView("login/login");
    }

    @GetMapping("/register")
    public ModelAndView register() {
        return new ModelAndView("login/register");
    }

    @GetMapping("/successLogin")
    public ModelAndView successLogin() {
        return new ModelAndView("login/successLogin");
    }

    @GetMapping("/faliureLogin")
    public ModelAndView faliureLogin() {
        return new ModelAndView("login/loginError");
    }

    @PostMapping("/insertUser")
    public ModelAndView insertUser(UserVo userDto) {
        log.info("insertUser ::: {}",userDto);
        int res = userService.insertUser(userDto);
        log.info("res ::: {}",res);

        if(res==1){
            return new ModelAndView("redirect:/index");
        }else{
            return new ModelAndView("redirect:/error/registerError");
        }
    }

}