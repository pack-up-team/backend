package com.swygbro.packup.login.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.swygbro.packup.security.jwt.JwtUtill;
import com.swygbro.packup.user.service.UserService;
import com.swygbro.packup.user.vo.UserVo;

import java.util.HashMap;
import java.util.Map;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/lgn")
public class LoginController {

    private final UserService userService;
    private final JwtUtill jwtUtill;

    @PostMapping("/login")
    public ResponseEntity<Map<String, Object>> login(@RequestBody UserVo loginUser) {
        try {        	
            // 사용자 인증 로직 (UserService에 메서드 추가 필요)
            UserVo authenticatedUser = userService.authenticateUser(loginUser.getUserId(), loginUser.getUserPw());
            
            if (authenticatedUser != null) {
                // JWT 토큰 생성 (90일)
                String token = jwtUtill.createToken(
                    authenticatedUser.getUserNm(),
                    authenticatedUser.getRole(),
                    authenticatedUser.getUserId(),
                    90L * 24 * 60 * 60 * 1000
                );
                
                Map<String, Object> response = new HashMap<>();
                response.put("success", true);
                response.put("token", token);
                response.put("userId", authenticatedUser.getUserId());
                response.put("username", authenticatedUser.getUserNm());
                response.put("role", authenticatedUser.getRole());
                
                return ResponseEntity.ok(response);
            } else {
                Map<String, Object> response = new HashMap<>();
                response.put("success", false);
                response.put("message", "Invalid credentials");
                
                return ResponseEntity.badRequest().body(response);
            }
        } catch (Exception e) {
            log.error("Login error: ", e);
            Map<String, Object> response = new HashMap<>();
            response.put("success", false);
            response.put("message", "Login failed");
            
            return ResponseEntity.internalServerError().body(response);
        }
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