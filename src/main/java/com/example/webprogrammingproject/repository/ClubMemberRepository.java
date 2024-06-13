package com.example.webprogrammingproject.repository;

import com.example.webprogrammingproject.domain.ClubMember;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ClubMemberRepository extends JpaRepository<ClubMember, Long> {
    Optional<ClubMember> findByClub_IdAndMember_Email(Long clubId, String memberEmail);
}
