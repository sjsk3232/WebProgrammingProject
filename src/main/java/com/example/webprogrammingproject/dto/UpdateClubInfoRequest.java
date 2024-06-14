package com.example.webprogrammingproject.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

@Getter
@Setter
@AllArgsConstructor
public class UpdateClubInfoRequest {
    private Long targetClubId;
    private String clubIntro;
    private MultipartFile img;
    private String advisorName;
    private String advisorMajor;
    private String advisorContact;
    private String regularMeeting;
    private MultipartFile applicationForm;
}