package com.swygbro.packup.sns.SignUP.entity;

import com.swygbro.packup.user.entity.User;
import jakarta.persistence.*;
import com.swygbro.packup.sns.SignUP.dto.JoinDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.*;

@Builder
@Table(name ="TBL_SOCIAL_LOGIN_INFO")
@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class SnsUser {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "SOCIAL_NO", nullable = false)
    private Long socialNo;

    @Column(name = "USER_ID", nullable = false)
    private String userId;

    @Column(name = "USER_NO", nullable = false)
    private int userNo;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "USER_ID", referencedColumnName = "USER_ID", insertable = false, updatable = false)
    private User user;

    @Column(name = "LOGIN_TYPE", nullable = false)
    private String loginType;

    @Column(name = "SOCIAL_ID", nullable = false)
    private String socialId;

    @Column(name = "REG_ID")
    private String regId;

    @Column(name = "REG_DT")
    private LocalDateTime regDt;

    public static SnsUser join(JoinDto joinDto, int userNo) {
        return SnsUser.builder()
                .userId(joinDto.getUSER_ID())
                .userNo(userNo)
                .loginType(joinDto.getLOGIN_TYPE())
                .socialId(joinDto.getSOCIAL_ID())
                .regId("")
                .regDt(LocalDateTime.now())
                .build();
    }

}
