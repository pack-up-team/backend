package com.swygbro.packup.sns.SignUP.controller;

import com.swygbro.packup.sns.Helper.socialLoginType;
import com.swygbro.packup.sns.SignUP.Service.JoinService;
import com.swygbro.packup.sns.SignUP.dto.JoinDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class SocialJoinController {

    private final JoinService joinService;

    @PostMapping("/join")
    public ResponseEntity<Void> joinSocial( @PathVariable() JoinDto dto) {
        joinService.joinSocial(dto);
        return ResponseEntity.status(HttpStatus.OK).build();
    }


}
