package com.example.webprogrammingproject.controller;

import com.example.webprogrammingproject.config.jwt.TokenProvider;
import com.example.webprogrammingproject.domain.Member;
import com.example.webprogrammingproject.dto.AddMemberRequest;
import com.example.webprogrammingproject.dto.KakaoUserInfoResponse;
import com.example.webprogrammingproject.dto.LoginResponse;
import com.example.webprogrammingproject.repository.MemberRepository;
import com.example.webprogrammingproject.service.KakaoService;
import com.example.webprogrammingproject.service.MemberService;
import com.example.webprogrammingproject.service.RefreshTokenService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.Duration;

@RequiredArgsConstructor
@Controller
public class MemberController {
    private final MemberService memberService;
    private final MemberRepository memberRepository;
    private final KakaoService kakaoService;
    private final TokenProvider tokenProvider;
    private final RefreshTokenService refreshTokenService;

    @PostMapping("/member")
    public ResponseEntity<Void> signup(@RequestBody AddMemberRequest request) {
        memberService.save(request);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/login/kakao")
    public ResponseEntity<LoginResponse> kakaoLogin(@RequestParam String code) {
        String accessToken = kakaoService.getAccessTokenFromKakao(code);
        KakaoUserInfoResponse userInfo = kakaoService.getUserInfo(accessToken);
        Member found = memberService.findById(userInfo.kakaoAccount.email);
        if(found == null) {
            found = memberRepository.save(
                    Member.builder()
                    .name(userInfo.kakaoAccount.name)
                    .email(userInfo.kakaoAccount.email)
                    .role("ROLE_MEMBER")
                    .build()
            );
        }

        LoginResponse loginResponse = new LoginResponse(
                found.getName(),
                tokenProvider.generateToken(found, Duration.ofDays(1)),
                "Bearer " + tokenProvider.generateToken(found, Duration.ofHours(1)),
                found.getRole()
        );
        refreshTokenService.save(found.getEmail(), loginResponse.getRefreshToken());

        return ResponseEntity.ok().body(loginResponse);
    }
}
