package com.swygbro.packup.security.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.UUID;

@Component
public class JwtUtill {

    private SecretKey key;

    public JwtUtill(@Value("${spring.jwt.secret}") String secret) {
        key = new SecretKeySpec(secret.getBytes(StandardCharsets.UTF_8), Jwts.SIG.HS256.key().build().getAlgorithm());
    }

    // jwt bearner 토큰으로 부터 username을 추출
    public String getUsername(String jws) {
        // 만약 "isAdmin"; true 필드를 원할 경우 String.class -> Boolean.class로 변경하기.
        return Jwts.parser().verifyWith(key).build().parseSignedClaims(jws).getPayload().get("username", String.class);
    }

    // jwt bearer 토큰으로부터 role을 추출
    public String getrole(String jws) {
        return Jwts.parser().verifyWith(key).build().parseSignedClaims(jws).getPayload().get("role", String.class);
    }

    // jwt bearer 토큰으로부터 userId을 추출
    public  String getUserId(String jws) {
        return Jwts.parser().verifyWith(key).build().parseSignedClaims(jws).getPayload().get("userId", String.class);
    }

    // jwt bearer 토큰이 만료 되었는지 확인
    public Boolean isExpired(String jws) {
        try{
            Date expire = Jwts.parser().verifyWith(key).build().parseSignedClaims(jws).getPayload().getExpiration();

            System.out.println("만료 시간: " + expire);
            return expire.before(new Date());

        }catch (ExpiredJwtException e){
            System.out.println("JWT가 이미 만료됨: " + e.getMessage());
            return true;
        }
    }

    /**
     * jwt 토큰 생성
     * @param username, role: payload에 넣을 사용자 정보
     * @param expiredMs: 만료 시간(밀리초 단위)
     * @return
     */
    public String createToken(String username, String role, String userId, Long expiredMs) {
        return Jwts.builder()
                .claim("username", username)
                .claim("role", role)
                .claim("userId", userId)
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis() + expiredMs))
                .signWith(key)
                .compact();
    }

}

