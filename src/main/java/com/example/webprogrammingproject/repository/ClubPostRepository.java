package com.example.webprogrammingproject.repository;

import com.example.webprogrammingproject.domain.ClubPost;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClubPostRepository extends JpaRepository<ClubPost, Long> {
}
