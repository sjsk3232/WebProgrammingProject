package com.example.webprogrammingproject.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class GetClubMemberApplicationResponse {
    private Long id;
    private String applicantName;
    private String applicationUrl;
    private String result;
    private LocalDateTime createdAt;

    public GetClubMemberApplicationResponse(
            Long id, String applicantName, String application, String result, LocalDateTime createdAt
    ) {
        final String URL_PREFIX = "https://storage.googleapis.com/bidmarkit-bucket/";

        this.id = id;
        this.applicantName = applicantName;
        this.applicationUrl = URL_PREFIX + application;
        this.result = result;
        this.createdAt = createdAt;
    }

    public void setApplicationUrl(String application) {
        final String URL_PREFIX = "https://storage.googleapis.com/bidmarkit-bucket/";
        this.applicationUrl = URL_PREFIX + application;
    }
}
