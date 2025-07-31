package com.swygbro.packup.security.oauth2;

import com.swygbro.packup.security.jwt.JwtUtill;
import com.swygbro.packup.sns.SignUP.dto.CustomOAuth2User;
import com.swygbro.packup.sns.SignUP.entity.SnsUser;
import com.swygbro.packup.sns.SignUP.repository.SnsSignUpRepo;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Collection;

@RequiredArgsConstructor
@Component
public class OAuth2SuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

    private final JwtUtill jwtUtil;
    private final SnsSignUpRepo snsSignUpRepo;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException {

        // ✅ 사용자 정보 가져오기
        CustomOAuth2User user = (CustomOAuth2User) authentication.getPrincipal();

        String username = user.getUserNm();
        String userId = user.getUserId();
        String socialId = user.getSocialId();
        String snsType = String.valueOf(user.getSocialLoginType());   // kakao, google, naver

        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
        String role = authorities.iterator().next().getAuthority();

        // ✅ 토큰 발급 및 쿠키 저장
        String token = jwtUtil.createToken(username, role, userId, 90L * 24 * 60 * 60 * 1000);
        ResponseCookie responseCookie = createCookie("Authorization", token);
        response.addHeader(HttpHeaders.SET_COOKIE, responseCookie.toString());
        // ✅ SNS 연동 정보 저장 (중복 방지)
        if (!snsSignUpRepo.existsByUserNoAndSnsType((user.getUserNo()), snsType)) {
            SnsUser snsUser = SnsUser.builder()
                    .userNo(user.getUserNo())
                    .loginType(snsType)
                    .socialId(socialId)
                    .build();
            snsSignUpRepo.save(snsUser);
        }

        // ✅ 리다이렉트
        response.sendRedirect("https://packup.swygbro.com");
    }

    private ResponseCookie createCookie(String key, String value) {
        return ResponseCookie.from(key, value)
                .httpOnly(false)
                .secure(true)
                .path("/")
                .maxAge(90 * 24 * 60 * 60)
                .sameSite("None")
                .build();
    }
}
