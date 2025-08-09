package com.swygbro.packup;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
@ActiveProfiles("test")
@TestPropertySource(properties = {
    "spring.jwt.secret=thisIsASecretKeyWithAtLeast32ByteLength123!!",
    "spring.datasource.url=jdbc:h2:mem:testdb;MODE=MySQL;DB_CLOSE_DELAY=-1;DB_CLOSE_ON_EXIT=FALSE",
    "spring.jpa.hibernate.ddl-auto=create-drop",
    "spring.data.redis.repositories.enabled=false",
    "spring.cache.type=none",
    "spring.mail.host=localhost"
})
class PackUpApplicationTests {

	@Test
	void contextLoads() {
		// Spring Boot 컨텍스트가 정상적으로 로드되는지 확인
	}

}
