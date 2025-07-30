package com.swygbro.packup.security.oauth2;

import com.swygbro.packup.security.jwt.JwtUtill;
import com.swygbro.packup.user.entity.User;
import com.swygbro.packup.user.vo.UserVo;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;

import java.io.IOException;
import java.util.Collection;
import java.util.Iterator;


@RequiredArgsConstructor
@Component
public class SnsHandler extends SimpleUrlAuthenticationSuccessHandler {
    private final JwtUtill jwtUtil;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication)  throws IOException, ServletException {

        //OAuth2User
        UserVo user = (UserVo) authentication.getPrincipal();

        String username = user.getUserNm();
        String userId = user.getUserId();

        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
        Iterator<? extends GrantedAuthority> iterator = authorities.iterator();
        GrantedAuthority auth = iterator.next();
        String role = auth.getAuthority();

        String token = jwtUtil.createToken(username, role,  userId,90*24*60*60*1000L);

        ResponseCookie responseCookie = createCookie("Authorization", token);
        response.addHeader(HttpHeaders.SET_COOKIE, responseCookie.toString());
        response.sendRedirect("https://packup.swygbro.com");
        //response.addCookie(createCookie("Authorization", token));
    }

    // 필요없으면 지우기
    private ResponseCookie createCookie(String key, String value) {
        
        ResponseCookie responseCookie = ResponseCookie.from(key, value)
                .httpOnly(false)
                .secure(true)
                .path("/")
                .maxAge(90 * 24 * 60 * 60)
                .sameSite("None")
                .build();


        return responseCookie;
    }

    }
}
