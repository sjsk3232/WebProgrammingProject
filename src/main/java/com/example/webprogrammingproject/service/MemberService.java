package com.example.webprogrammingproject.service;

import com.example.webprogrammingproject.domain.Member;
import com.example.webprogrammingproject.dto.AddMemberRequest;
import com.example.webprogrammingproject.repository.MemberRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class MemberService {
    private final MemberRepository memberRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    @Transactional
    public Member save(AddMemberRequest dto) {
        return memberRepository.save(
                Member.builder()
                        .email(dto.getEmail())
                        .password(bCryptPasswordEncoder.encode(dto.getPassword()))
                        .name(dto.getName())
                        .birthday(dto.getBirthday())
                        .major(dto.getMajor())
                        .studentNumber(dto.getStudentNumber())
                        .phoneNumber(dto.getPhoneNumber())
                        .role("ROLE_MEMBER")
                        .build()
        );
    }

    public Member findById(String memberId) {
        return memberRepository.findById(memberId)
                .orElse(null);
    }
}
