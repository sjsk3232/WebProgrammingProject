package com.example.webprogrammingproject.dto;

import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

@Getter
@Setter
public class AddClubMemberApplicationRequest {
    private Long clubId;
    private MultipartFile application;
}
