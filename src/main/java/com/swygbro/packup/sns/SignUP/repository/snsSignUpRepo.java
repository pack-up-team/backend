package com.swygbro.packup.sns.SignUP.repository;

import com.swygbro.packup.sns.SignUP.entity.snsUser;
import org.springframework.data.jpa.repository.JpaRepository;

public interface snsSignUpRepo extends JpaRepository<snsUser, Long> {

    boolean existsByEmailAndLoginType(String socialId, String loginType);

}
