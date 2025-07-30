package com.swygbro.packup.user.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.swygbro.packup.user.entity.User;

public interface UserRepository extends JpaRepository<User, Long> {

    boolean existsByUserId(String userId);
}
