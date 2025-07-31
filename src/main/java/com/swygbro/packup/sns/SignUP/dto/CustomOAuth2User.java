package com.swygbro.packup.sns.SignUP.dto;

import com.swygbro.packup.sns.Helper.socialLoginType;
import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.core.user.OAuth2User;
import java.lang.*;
import java.util.*;

@Getter
public class CustomOAuth2User implements OAuth2User {
    private final String userId;
    private final String userNm;
    private final String nameKey;
    private final int userNo;
    private final Collection<? extends GrantedAuthority> authorities;
    private final Map<String, Object> attributes;
    private final socialLoginType socialLoginType;

    public CustomOAuth2User(Collection<? extends GrantedAuthority> authorities,
                            Map<String, Object> attributes,
                            String userId,
                            String userNm,
                            String nameKey, int userNo, socialLoginType socialLoginType) {
        this.authorities = authorities;
        this.attributes = attributes;
        this.userId = userId;
        this.userNm = userNm;
        this.nameKey = nameKey;
        this.userNo = userNo;
        this.socialLoginType = socialLoginType;
    }

    @Override
    public Map<String, Object> getAttributes() {
        return attributes;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getName() {
        return userId;
    }

    // SNS 식별 ID
    public String getSocialId() {
        Object id = attributes.get("id");
        if (id == null && attributes.get("response") instanceof Map<?, ?> response) {
            id = response.get("id");
        } else if (id == null && attributes.get("sub") != null) {
            id = attributes.get("sub");
        }
        return id != null ? id.toString() : null;
    }

    // enum 기반 반환
    public socialLoginType getSocialLoginType() {
        return socialLoginType;
    }


}
