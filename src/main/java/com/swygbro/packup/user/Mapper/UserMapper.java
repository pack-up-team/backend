package com.swygbro.packup.user.Mapper;

import org.apache.ibatis.annotations.Mapper;

import com.swygbro.packup.config.CustomUserDetails;
import com.swygbro.packup.user.vo.UserVo;

@Mapper
public interface UserMapper {

    int insertUser(UserVo userDto);

    CustomUserDetails selectUserById(String userId);

}
