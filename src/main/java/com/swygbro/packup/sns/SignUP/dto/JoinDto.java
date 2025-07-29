package com.swygbro.packup.sns.SignUP.dto;

import com.swygbro.packup.user.entity.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class JoinDto {

    String USER_NO;
    String USER_ID;

    public static JoinDto trnasDTO(User user){
        return new JoinDto(user.getUserId(), user.getUserId());
    }
}
