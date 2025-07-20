package com.swygbro.packup.register.service;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.swygbro.packup.register.mapper.RegisterMapper;
import com.swygbro.packup.user.vo.UserVo;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class RegisterService {

    private final RegisterMapper registerMapper;

    public int registerUser(UserVo userVo) {
        System.out.println("service userVo : "+userVo);
        return registerMapper.insertUser(userVo);
    }

    public int chkId(String userId) {
        return registerMapper.chkId(userId) ;
    }

}
