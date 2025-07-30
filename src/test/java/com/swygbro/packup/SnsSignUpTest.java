package com.swygbro.packup;

import com.swygbro.packup.sns.SignUP.Service.JoinService;
import com.swygbro.packup.sns.SignUP.dto.JoinDto;
import com.swygbro.packup.sns.SignUP.entity.SnsUser;
import com.swygbro.packup.sns.SignUP.repository.SnsSignUpRepo;
import com.swygbro.packup.user.entity.User;
import com.swygbro.packup.user.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class SnsSignUpTest {

    @InjectMocks
    private JoinService joinService;

    @Mock
    private UserRepository userRepository;

    @Mock
    private SnsSignUpRepo snsSignUpRepo;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Test
    void 소셜회원가입_성공() {
        // given
        JoinDto joinDto = JoinDto.builder()
                .USER_ID("testUser")
                .USER_NM("홍길동")
                .EMAIL("test@example.com")
                .LOGIN_TYPE("google")
                .SOCIAL_ID("google-123")
                .build();

        User user = User.builder()
                .userNo(1)
                .userId(joinDto.getUSER_ID())
                .userNm(joinDto.getUSER_NM())
                .email(joinDto.getEMAIL())
                .build();


        when(snsSignUpRepo.existsBySocialIdAndLoginType(joinDto.getSOCIAL_ID(), joinDto.getLOGIN_TYPE())).thenReturn(false);
        when(userRepository.save(any(User.class))).thenReturn(user);
        when(passwordEncoder.encode(any())).thenReturn("encodedPassword");

        // when
        joinService.joinSocial(joinDto);

        // then
        verify(userRepository, times(2)).save(any(User.class)); // ✅ 수정: 저장 2회
        verify(snsSignUpRepo, times(1)).save(any(SnsUser.class));

    }

    @Test
    void 소셜회원가입_실패_이미가입된소셜() {
        // given
        JoinDto joinDto = JoinDto.builder()
                .USER_ID("testUser")
                .USER_NM("홍길동")
                .EMAIL("test@example.com")
                .LOGIN_TYPE("google")
                .SOCIAL_ID("google-123")
                .build();

        when(snsSignUpRepo.existsBySocialIdAndLoginType(joinDto.getSOCIAL_ID(), joinDto.getLOGIN_TYPE())).thenReturn(true);

        // when & then
        IllegalStateException exception = assertThrows(IllegalStateException.class, () -> {
            joinService.joinSocial(joinDto);
        });

        assertEquals("이미 가입된 sns 계정입니다.", exception.getMessage());
        verify(userRepository, never()).save(any());
        verify(snsSignUpRepo, never()).save(any());
    }
}
