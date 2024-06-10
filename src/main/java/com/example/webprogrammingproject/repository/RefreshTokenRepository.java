package com.example.webprogrammingproject.repository;

import com.example.webprogrammingproject.domain.RefreshToken;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RefreshTokenRepository extends JpaRepository<RefreshToken, Long> {
    Optional<RefreshToken> findByMemberId(String memberId);
    Optional<RefreshToken> findByRefreshToken(String refreshToken);
}
