package com.example.webprogrammingproject.dto;

import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

@Getter
@Setter
public class UpdateClubPostRequest {
    private Long postId;
    private Boolean isPublic;
    private String title;
    private String content;
    private MultipartFile multipartFile;
    private String multipart;
}
