package com.swygbro.packup;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
@TestPropertySource(properties = {
    "spring.jwt.secret=thisIsASecretKeyWithAtLeast32ByteLength123!!",
    "spring.datasource.url=jdbc:h2:mem:testdb",
    "spring.jpa.hibernate.ddl-auto=create-drop"
})
class PackUpApplicationTests {

	@Test
	void contextLoads() {
		// Spring Boot 컨텍스트가 정상적으로 로드되는지 확인
	}

}
