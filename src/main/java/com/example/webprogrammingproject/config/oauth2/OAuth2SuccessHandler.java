package com.example.webprogrammingproject.config.oauth2;

import com.example.webprogrammingproject.config.jwt.TokenProvider;
import com.example.webprogrammingproject.domain.Member;
import com.example.webprogrammingproject.dto.LoginResponse;
import com.example.webprogrammingproject.service.RefreshTokenService;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.time.Duration;
import java.util.Collection;
import java.util.Iterator;

@RequiredArgsConstructor
@Component
public class OAuth2SuccessHandler implements AuthenticationSuccessHandler {
    private final TokenProvider tokenProvider;
    private final RefreshTokenService refreshTokenService;
    private final ObjectMapper mapper = new ObjectMapper();

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
                                        Authentication authentication
    ) throws IOException {
        CustomOAuth2UserDetails customUserDetails = (CustomOAuth2UserDetails) authentication.getPrincipal();
        String username = customUserDetails.getUsername(), nickname = customUserDetails.getNickName();

        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
        Iterator<? extends GrantedAuthority> iterator = authorities.iterator();
        GrantedAuthority auth = iterator.next();
        String role = auth.getAuthority();
        Member member = Member.builder().email(username).name(nickname).role(role).build(); // 멤버 정보

        LoginResponse loginResponse = new LoginResponse(
                member.getName(),
                tokenProvider.generateToken(member, Duration.ofDays(1)),
                "Bearer " + tokenProvider.generateToken(member, Duration.ofHours(1)),
                member.getRole()
        );
        refreshTokenService.save(username, loginResponse.getRefreshToken());
        String result = mapper.writeValueAsString(loginResponse);
        response.addHeader("Content-Type", "application/json; charset=UTF-8");
        response.getWriter().write(result);
    }
}