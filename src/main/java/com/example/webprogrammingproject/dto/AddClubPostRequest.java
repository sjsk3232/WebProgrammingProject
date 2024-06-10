package com.example.webprogrammingproject.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

@Getter
@Setter
@AllArgsConstructor
public class AddClubPostRequest {
    private Long clubId;
    private String postType;
    private Boolean isPublic;
    private String title;
    private String content;
    private MultipartFile multipartFile;
    private String multipart;
}
