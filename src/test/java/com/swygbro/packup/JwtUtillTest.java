package com.swygbro.packup;

import io.jsonwebtoken.ExpiredJwtException;
import com.swygbro.packup.security.jwt.JwtUtill;
import org.aspectj.weaver.patterns.IToken;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import static org.junit.jupiter.api.Assertions.*;

import java.util.UUID;

@SpringBootTest(properties = "spring.jwt.secret=thisIsASecretKeyWithAtLeast32ByteLength123!!")
public class JwtUtillTest {

    @Autowired
    private JwtUtill jwtUtill;

    @Test
    void createAndValidateToken() {
        // given
        String username = "testuser";
        String role = "USER";
        String userId = "abc124";
        long expiredMs = 1000 * 60 * 10;

        // when
        String token = jwtUtill.createToken(username, role, userId, expiredMs);

        // then
        assertEquals(username, jwtUtill.getUsername(token));
        assertEquals(role, jwtUtill.getrole(token));
        assertEquals(userId, jwtUtill.getUserId(token));
        assertFalse(jwtUtill.isExpired(token));

    }

    @Test
    void testTokenExpiration() throws InterruptedException {
        // given
        String token = jwtUtill.createToken("expiredUser", "USER", "user1", 1000L); // 1초짜리

        // wait 2 seconds to ensure it's expired
        Thread.sleep(2000);

        // then
        assertTrue(jwtUtill.isExpired(token));
    }
}
