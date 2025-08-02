package com.swygbro.packup.sns.SignUP.controller;

import com.swygbro.packup.sns.Helper.socialLoginType;
import com.swygbro.packup.sns.SignUP.Service.JoinService;
import com.swygbro.packup.sns.SignUP.dto.JoinDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class SocialJoinController {

    private final JoinService joinService;

    // 회원가입 처리
    @PostMapping("/join")
    public ResponseEntity<Void> joinSocial(@RequestBody JoinDto dto) {
        joinService.joinSocial(dto);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    // 소셜 계정 가입 여부 확인 처리 -> OAuth2SuccessHandler에서 처리함. 개별 처리 불필요.


}
