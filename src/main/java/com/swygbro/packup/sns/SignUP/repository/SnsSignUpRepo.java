package com.swygbro.packup.sns.SignUP.repository;

import com.swygbro.packup.sns.SignUP.entity.SnsUser;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface SnsSignUpRepo extends JpaRepository<SnsUser, Long> {

    //boolean existsBysocialIdAndLoginType(String socialId, String loginType);
    boolean existsBySocialIdAndLoginType(String socialId, String loginType);

    Optional<SnsUser> findBySocialIdAndLoginType(String socialId, String name);

    int countByUserNo(int userNo);

    boolean existsByUserNoAndLoginType(int userNo, String snsType);

    Optional<Object> findByUserNoAndLoginType(int userNo, String snsType);
}
