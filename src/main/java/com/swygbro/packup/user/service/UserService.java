package com.swygbro.packup.user.service;

import lombok.RequiredArgsConstructor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.swygbro.packup.user.Mapper.UserMapper;
import com.swygbro.packup.user.entity.User;
import com.swygbro.packup.user.vo.UserVo;

@RequiredArgsConstructor
@Service
public class UserService {

    private final UserMapper userMapper;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public int insertUser(UserVo userDto){
        //패스워드 암호화
        userDto.setUserPw(passwordEncoder.encode(userDto.getUserPw()));
        return userMapper.insertUser(userDto);
    }

    public UserVo getUserInfo(String userId) {
        return userMapper.getUserInfo(userId);
    }

    public int updateUser(UserVo userVo) {
        return userMapper.updateUser(userVo);
    }

}