package com.example.webprogrammingproject.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class GetClubPostResponse {
    private Long postId;
    private String postType;
    private String title;
    private String content;
    private String multipartUrl;
    private LocalDateTime createdAt;

    public GetClubPostResponse(
            Long postId, String postType, String title, String content, String multipartUrl, LocalDateTime createdAt
    ) {
        final String URL_PREFIX = "https://storage.googleapis.com/bidmarkit-bucket/";

        this.postId = postId;
        this.postType = postType;
        this.title = title;
        this.content = content;
        this.createdAt = createdAt;

        if(postType.equals("사진"))
            this.multipartUrl = URL_PREFIX + multipartUrl;
        else
            this.multipartUrl = multipartUrl;
    }

    public void setMultipartUrl(String multipartUrl) {
        final String URL_PREFIX = "https://storage.googleapis.com/bidmarkit-bucket/";

        if(this.postType.equals("사진"))
            this.multipartUrl = URL_PREFIX + multipartUrl;
        else
            this.multipartUrl = multipartUrl;
    }
}
