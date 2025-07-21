package com.swygbro.packup.user.Mapper;

import org.apache.ibatis.annotations.Mapper;

import com.swygbro.packup.config.CustomUserDetails;
import com.swygbro.packup.user.vo.UserVo;

@Mapper
public interface UserMapper {

    int insertUser(UserVo userDto);

    CustomUserDetails selectUserById(String userId);

    int updateLastLoginDate(String userId);

    UserVo getUserInfo(String userId);

    int updateUser(UserVo userVo);

}
