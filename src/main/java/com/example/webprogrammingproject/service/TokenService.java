package com.example.webprogrammingproject.service;

import com.example.webprogrammingproject.config.jwt.TokenProvider;
import com.example.webprogrammingproject.domain.Member;
import com.example.webprogrammingproject.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Duration;

@RequiredArgsConstructor
@Service
public class TokenService {

    private final TokenProvider tokenProvider;
    private final RefreshTokenService refreshTokenService;
    private final MemberRepository memberRepository;

    public String createNewAccessToken(String refreshToken) {
        // 토큰 유효성 검사에 실패하면 예외 발생
        if(!tokenProvider.validToken(refreshToken) && refreshTokenService.findByRefreshToken(refreshToken) == null) {
            throw new IllegalArgumentException("Unexpected token");
        }

        String memberId = refreshTokenService.findByRefreshToken(refreshToken).getMemberId();
        Member member = memberRepository.findByEmail(memberId).orElseThrow(
                () -> new IllegalArgumentException("createNewAccessToken error: not found member email")
        );

        return tokenProvider.generateToken(member, Duration.ofHours(1));
    }

    public String getMemberEmail(String token) {
        if(token.startsWith("Bearer ")) token = token.substring(7);
        return tokenProvider.getMemberId(token);
    }
}
