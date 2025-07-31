package com.swygbro.packup.security.oauth2;

import com.swygbro.packup.sns.Helper.socialLoginType;
import com.swygbro.packup.sns.SignUP.Service.JoinService;
import com.swygbro.packup.sns.SignUP.dto.JoinDto;
import com.swygbro.packup.sns.SignUP.entity.SnsUser;
import com.swygbro.packup.sns.SignUP.repository.SnsSignUpRepo;
import com.swygbro.packup.user.entity.User;
import com.swygbro.packup.user.repository.UserRepository;
import com.swygbro.packup.user.service.UserService;
import com.swygbro.packup.sns.SignUP.dto.CustomOAuth2User;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import java.util.*;
import java.lang.*;

@RequiredArgsConstructor
@Service
public class CustomOAuth2UserService extends DefaultOAuth2UserService implements OAuth2UserService<OAuth2UserRequest, OAuth2User>{

    private final JoinService joinService;
    private final UserRepository userRepository;
    private final SnsSignUpRepo snsSignUpRepo;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) {
        OAuth2User oAuth2User = super.loadUser(userRequest);

        String registrationId = userRequest.getClientRegistration().getRegistrationId(); // ex: "kakao"
        socialLoginType socialType = socialLoginType.valueOf(registrationId.toUpperCase());

        Map<String, Object> attributes = oAuth2User.getAttributes();
        String socialId = extractSocialId(attributes, socialType); // 카카오 id, 네이버 response.id, 구글 sub
        String email = extractEmail(attributes, socialType); // 있을 수도 있고, 없을 수도 있음

        // TODO 카카오 승인 완료 시 변경 필
        User user = null;

        if (socialType == socialLoginType.KAKAO) {
            // ✅ 카카오: 이메일 없이 처리
            Optional<SnsUser> snsUserOpt = snsSignUpRepo.findBySocialIdAndLoginType(socialId, socialType.name());
            if (snsUserOpt.isPresent()) {
                user = userRepository.findById((long) snsUserOpt.get().getUserNo()).orElseThrow();
            } else {
                // 자동 회원가입
                JoinDto dto = JoinDto.builder()
                        .USER_ID(socialType.name().toLowerCase() + "_" + socialId)
                        .USER_NM("packUp#" + UUID.randomUUID().toString().substring(0, 6))
                        .LOGIN_TYPE(socialType.name())
                        .USER_PW(generateRandomPassword())
                        .SOCIAL_ID(socialId)
                        .build();
                joinService.joinSocial(dto);
                user = (User) userRepository.findByUserId(dto.getUSER_ID()).orElseThrow();
            }
        } else {
            // ✅ 네이버 / 구글: 이메일로 먼저 확인
            var userOpt = userRepository.findByEmail(email);
            if (userOpt.isPresent()) {
                user = (User) userOpt.get();
            } else {
                JoinDto dto = JoinDto.builder()
                        .USER_ID(email) // 이메일 기반으로 저장
                        .USER_NM("packUp#" + UUID.randomUUID().toString().substring(0, 6))
                        .LOGIN_TYPE(socialType.name())
                        .EMAIL(email)
                        .USER_PW(generateRandomPassword())
                        .SOCIAL_ID(socialId)
                        .build();
                joinService.joinSocial(dto);
                user = (User) userRepository.findByUserId(dto.getUSER_ID()).orElseThrow();
            }
        }

        String role = user.getRole();
        if(role == null || role.trim().isEmpty()){
            role = "ROLE_USER";
        }

        return new CustomOAuth2User(
                Collections.singleton(new SimpleGrantedAuthority(role)),
                attributes,
                "id",
                user.getUserId(),
                user.getUserNm(),
                user.getUserNo(),
                socialType
        );
    }

    private String generateRandomPassword() {
        String lowerCase = "abcdefghijklmnopqrstuvwxyz";
        String numbers = "0123456789";
        String specials = "!@#$%^&*";
        String all = lowerCase + numbers + specials;

        Random random = new Random();
        StringBuilder sb = new StringBuilder();

        // 필수 구성 요소 1개씩 삽입
        sb.append(lowerCase.charAt(random.nextInt(lowerCase.length())));
        sb.append(numbers.charAt(random.nextInt(numbers.length())));
        sb.append(specials.charAt(random.nextInt(specials.length())));

        // 나머지 랜덤 문자로 채움 (총 8자 이상)
        for (int i = 0; i < 5; i++) {
            sb.append(all.charAt(random.nextInt(all.length())));
        }

        // 문자열 섞기 (순서 보안)
        List<Character> chars = new ArrayList<>();
        for (char c : sb.toString().toCharArray()) {
            chars.add(c);
        }
        Collections.shuffle(chars);

        StringBuilder finalPw = new StringBuilder();
        for (char c : chars) {
            finalPw.append(c);
        }

        return finalPw.toString();
    }

    private String extractSocialId(Map<String, Object> attributes, socialLoginType type) {
        if (type == socialLoginType.KAKAO) {
            return "kakao_" + attributes.get("id");
        } else if (type == socialLoginType.NAVER) {
            Map<String, Object> response = (Map<String, Object>) attributes.get("response");
            return "naver_" + response.get("id");
        } else if (type == socialLoginType.GOOGLE) {
            return "google_" + attributes.get("sub");
        }
        throw new RuntimeException("지원하지 않는 소셜 로그인 타입입니다.");
    }

    private String extractEmail(Map<String, Object> attributes, socialLoginType type) {
        if (type == socialLoginType.KAKAO) {
            Map<String, Object> account = (Map<String, Object>) attributes.get("kakao_account");
            return (String) account.get("email");
        } else if (type == socialLoginType.NAVER) {
            Map<String, Object> response = (Map<String, Object>) attributes.get("response");
            return (String) response.get("email");
        } else if (type == socialLoginType.GOOGLE) {
            return (String) attributes.get("email");
        }
        return null;
    }

}
