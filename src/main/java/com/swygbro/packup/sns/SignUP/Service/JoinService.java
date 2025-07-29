package com.swygbro.packup.sns.SignUP.Service;

import com.swygbro.packup.sns.SignUP.dto.JoinDto;
import com.swygbro.packup.sns.SignUP.entity.snsUser;
import com.swygbro.packup.sns.SignUP.repository.snsSignUpRepo;
import com.swygbro.packup.user.entity.User;
import com.swygbro.packup.user.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@RequiredArgsConstructor
@Service
public class JoinService {
    private final UserRepository userRepository;
    private final UserRepository joinRepository;
    private final snsSignUpRepo snsSignUpRepo;

    @Transactional
    public void joinSocial(JoinDto joinDto) {

        // 1. 중복 체크
        if(snsSignUpRepo.existsByEmailAndLoginType(joinDto.getSOCIAL_ID(), joinDto.getLOGIN_TYPE())){
            throw new IllegalStateException("이미 가입된 sns 계정입니다.");
        }

        // user 테이블 저장
        User user = User.builder()
                .userId(joinDto.getUSER_ID())
                .userNm(joinDto.getUSER_NM())
                .email(joinDto.getEMAIL())
                .build();
        User savedUser = userRepository.save(user);

        // 3. snsUser 저장
        snsUser snsUser = snsUser.builder()
                .userId(savedUser.getUserId())
                .userNo(savedUser.getUserNo())
                .loginType(joinDto.getLOGIN_TYPE())
                .socialId(joinDto.getSOCIAL_ID())
                .regId("system")
                .regDt(LocalDateTime.now())
                .build();

        snsSignUpRepo.save(snsUser);
    }
}
