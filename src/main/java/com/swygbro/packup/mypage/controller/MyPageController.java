package com.swygbro.packup.mypage.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.swygbro.packup.user.service.UserService;
import com.swygbro.packup.user.vo.UserVo;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping("/mypage")
public class MyPageController {

    private final UserService userService;
    private final BCryptPasswordEncoder passwordEncoder;

    @GetMapping("/mypage")
    public String mypage(Authentication authentication, Model model) {
        String userId = authentication.getName();
        log.info("MyPage accessed by user: {}", userId);
        
        UserVo userInfo = userService.getUserInfo(userId);
        if (userInfo != null) {
            userInfo.setUserPw("");
        }
        
        model.addAttribute("userInfo", userInfo);
        return "mypage/mypage";
    }

    @PostMapping("/updateUser")
    @ResponseBody
    public ResponseEntity<Map<String, Object>> updateUser(@RequestBody UserVo userVo, Authentication authentication) {
        Map<String, Object> response = new HashMap<>();
        
        try {
            String userId = authentication.getName();
            userVo.setUpdId(userId);
            
            if (userVo.getUserPw() != null && !userVo.getUserPw().trim().isEmpty()) {
                userVo.setUserPw(passwordEncoder.encode(userVo.getUserPw()));
            } else {
                userVo.setUserPw(null);
            }
            
            int result = userService.updateUser(userVo);
            
            if (result > 0) {
                response.put("success", true);
                response.put("message", "정보가 성공적으로 수정되었습니다.");
            } else {
                response.put("success", false);
                response.put("message", "정보 수정에 실패했습니다.");
            }
            
            return ResponseEntity.ok(response);
            
        } catch (Exception e) {
            log.error("Error updating user info: ", e);
            response.put("success", false);
            response.put("message", "정보 수정 중 오류가 발생했습니다.");
            
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }
}