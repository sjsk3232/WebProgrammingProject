package com.example.webprogrammingproject.repository;

import com.example.webprogrammingproject.domain.Club;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClubRepository extends JpaRepository<Club, Long> {
}
