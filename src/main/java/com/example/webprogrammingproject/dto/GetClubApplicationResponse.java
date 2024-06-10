package com.example.webprogrammingproject.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
public class GetClubApplicationResponse {
    private Long id;
    private String applicantEmail;
    private String applicantName;
    private String applicantMajor;
    private String applicantStudentNumber;
    private String clubType;
    private String clubName;
    private String advisorName;
    private String advisorMajor;
    private String advisorContact;
    private String result;
    private String rejectionReason;
    private LocalDateTime createdAt;
}
