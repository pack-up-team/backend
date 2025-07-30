package com.swygbro.packup.sns.SignUP.repository;

import com.swygbro.packup.sns.SignUP.entity.SnsUser;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SnsSignUpRepo extends JpaRepository<SnsUser, Long> {

    //boolean existsBysocialIdAndLoginType(String socialId, String loginType);
    boolean existsBySocialIdAndLoginType(String socialId, String loginType);
}
