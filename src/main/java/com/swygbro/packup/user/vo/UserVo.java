package com.swygbro.packup.user.vo;

import java.util.Date;

import com.swygbro.packup.common.vo.CommonVo;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class UserVo extends CommonVo{
    private int userNo;
    private String userId;
    private String userPw;
    private String email;
    private String userNm;
    private String phoneNum;
    private String gender;
    private Date lastLoginDt;
    private String role;
    private char useYn;
    private char delYn;
    private int lgnFailCnt;
    private String personalInfoAcq;
    private String infoAcq;
}
