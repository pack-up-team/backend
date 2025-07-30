package com.swygbro.packup.user.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Table(name = "TBL_USER")
@Data
@NoArgsConstructor
@Entity
public class User {

    @Id
    private String userId;

    private int userNo;
    private String userPw;
    private String userNm;
    private String email;
    private String phoneNum;
    private char gender;
    private String address;
    private String role;
    private char useYn;
    private char delYn;
    private char personalInfoAcq;

    @Builder
    public User(String userId, int userNo,  String userPw, String userNm, String email, String phoneNum, char gender, String address, String role, char useYn, char delYn, char personalInfoAcq ){
        this.userId = userId;
        this.userNo = userNo;
        this.userPw = userPw;
        this.userNm = userNm;
        this.email = email;
        this.phoneNum = phoneNum;
        this.gender = gender;
        this.address = address;
        this.role = role;
        this.useYn = useYn;
        this.delYn = delYn;
        this.personalInfoAcq = personalInfoAcq;
    }

    // sns 로그인
    public User(int userNo, String userId){
        this.userId = userId;
        this.userNo = userNo;
    }



    
}
