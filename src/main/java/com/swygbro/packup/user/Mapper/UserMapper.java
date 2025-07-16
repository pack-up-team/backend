package com.swygbro.packup.user.Mapper;

import org.apache.ibatis.annotations.Mapper;

import com.swygbro.packup.config.CustomUserDetails;
import com.swygbro.packup.user.dto.UserDto;

@Mapper
public interface UserMapper {

    int insertUser(UserDto userDto);

    CustomUserDetails selectUserById(String userId);

}
