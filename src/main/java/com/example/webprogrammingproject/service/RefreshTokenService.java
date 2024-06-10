package com.example.webprogrammingproject.service;

import com.example.webprogrammingproject.domain.RefreshToken;
import com.example.webprogrammingproject.repository.RefreshTokenRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@RequiredArgsConstructor
@Service
public class RefreshTokenService {
    private final RefreshTokenRepository refreshTokenRepository;

    public RefreshToken findByRefreshToken(String refreshToken) {
        return refreshTokenRepository.findByRefreshToken(refreshToken)
                .orElseThrow(() -> new IllegalArgumentException("Unexpected token"));
    }

    @Transactional
    public RefreshToken save(String memberId, String token) {
        Optional<RefreshToken> found = refreshTokenRepository.findByMemberId(memberId);
        if(found.isPresent()) {
            RefreshToken refreshToken = found.get();
            refreshToken.update(token);
            refreshTokenRepository.save(refreshToken);
            return refreshToken;
        }
        return refreshTokenRepository.save(RefreshToken.builder().memberId(memberId).refreshToken(token).build());
    }
}