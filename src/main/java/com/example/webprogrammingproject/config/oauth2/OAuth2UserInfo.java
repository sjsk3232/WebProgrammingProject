package com.example.webprogrammingproject.config.oauth2;

import com.example.webprogrammingproject.domain.Member;
import jakarta.security.auth.message.AuthException;
import lombok.Builder;

import java.util.Map;

@Builder
public record OAuth2UserInfo(
        String name,
        String email
) {

    public static OAuth2UserInfo of(String registrationId, Map<String, Object> attributes) throws AuthException {
        return switch (registrationId) { // registration id별로 userInfo 생성
            case "kakao" -> ofKakao(attributes);
            default -> throw new AuthException("illegal registration id");
        };
    }

    private static OAuth2UserInfo ofKakao(Map<String, Object> attributes) {
        Map<String, Object> account = (Map<String, Object>) attributes.get("kakao_account");
        Map<String, Object> profile = (Map<String, Object>) account.get("profile");
        if(!(boolean) account.get("has_email")) throw new IllegalArgumentException("kakao account has no email");

        return OAuth2UserInfo.builder()
                .name((String) profile.get("nickname"))
                .email((String) account.get("email"))
                .build();
    }

    public Member toEntity() {
        return Member.builder()
                .name(name)
                .email(email)
                .role("ROLE_MEMBER")
                .build();
    }
}