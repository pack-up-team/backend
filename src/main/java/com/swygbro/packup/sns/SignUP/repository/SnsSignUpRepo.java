package com.swygbro.packup.sns.SignUP.repository;

import com.swygbro.packup.sns.SignUP.entity.SnsUser;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SnsSignUpRepo extends JpaRepository<SnsUser, Long> {

    boolean existsByEmailAndLoginType(String socialId, String loginType);
    Object existsBySocialIdAndLoginType(String socialId, String loginType);
}
