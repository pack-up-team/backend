package com.swygbro.packup.sns.SignUP.Service;

import com.swygbro.packup.sns.SignUP.dto.JoinDto;
import com.swygbro.packup.sns.SignUP.entity.SnsUser;
import com.swygbro.packup.sns.SignUP.repository.SnsSignUpRepo;
import com.swygbro.packup.user.entity.User;
import com.swygbro.packup.user.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class JoinService {
    private final UserRepository userRepository;
    private final UserRepository joinRepository;
    private final SnsSignUpRepo snsSignUpRepo;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public void joinSocial(JoinDto joinDto) {

        // 1. 중복 체크
        if(snsSignUpRepo.existsByEmailAndLoginType(joinDto.getSOCIAL_ID(), joinDto.getLOGIN_TYPE())){
            throw new IllegalStateException("이미 가입된 sns 계정입니다.");
        }

        // 2. 일반회원 유저 있는지 확인
        if (joinDto.getUSER_ID() != null && userRepository.existsByUserId(joinDto.getUSER_ID())) {
            throw new IllegalStateException("이미 일반가입된 이메일입니다. 소셜 가입이 불가합니다.");
        }

        // 3. 비밀번호 자동 생성 + 암호화
        String rawPassword = generateSecurePassword();
        String encodedPw = passwordEncoder.encode(rawPassword);

        // 4. user 테이블 저장
        User user = User.builder()
                .userId(joinDto.getUSER_ID())
                .userNm(joinDto.getUSER_NM())
                .email(joinDto.getEMAIL())
                .build();
        User savedUser = userRepository.save(user);

        // 5. 닉네임 packUp#회원번호
        savedUser.setUserNm("packUp#" + savedUser.getUserNo());
        userRepository.save(savedUser);

        // 4. snsUser 저장
        SnsUser snsUser = snsUser.builder()
                .userId(savedUser.getUserId())
                .userNo(savedUser.getUserNo())
                .loginType(joinDto.getLOGIN_TYPE())
                .socialId(joinDto.getSOCIAL_ID())
                .regId("system")
                .regDt(LocalDateTime.now())
                .build();

        snsSignUpRepo.save(snsUser);
    }

    private String generateSecurePassword() {
        String lower = RandomStringUtils.random(4, true, false); // 소문자
        String digit = RandomStringUtils.random(2, false, true); // 숫자
        String special = RandomStringUtils.random(2, "!@#$%&*"); // 특수문자
        return StringShuffle(lower + digit + special);
    }

    private String StringShuffle(String input) {
        List<Character> chars = input.chars()
                .mapToObj(c -> (char) c)
                .collect(Collectors.toList());
        Collections.shuffle(chars); // 리스트 요소 무작위 섞기

        StringBuilder sb = new StringBuilder();
        for (char c : chars) {
            sb.append(c);
        }

        return sb.toString();
    }

}
