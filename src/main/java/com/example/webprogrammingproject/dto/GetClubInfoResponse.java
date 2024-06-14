package com.example.webprogrammingproject.dto;

import com.example.webprogrammingproject.domain.Club;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class GetClubInfoResponse {
    private Long id;
    private String clubType;
    private String clubName;
    private String clubIntro;
    private String clubImg;
    private String advisorName;
    private String advisorMajor;
    private String advisorContact;
    private String regularMeeting;
    private LocalDateTime createdAt;

    @Builder
    public GetClubInfoResponse(
            Long id, String clubType, String clubName, String clubIntro, String clubImg,
            String advisorName, String advisorMajor, String advisorContact,
            String regularMeeting, LocalDateTime createdAt
    ) {
        final String URL_PREFIX = "https://storage.googleapis.com/bidmarkit-bucket/";

        this.id = id;
        this.clubType = clubType;
        this.clubName = clubName;
        this.clubIntro = clubIntro;
        this.clubImg = clubImg == null ? null : URL_PREFIX + clubImg;
        this.advisorName = advisorName;
        this.advisorMajor = advisorMajor;
        this.advisorContact = advisorContact;
        this.regularMeeting = regularMeeting;
        this.createdAt = createdAt;
    }

    public static GetClubInfoResponse of(Club found) {
        return GetClubInfoResponse.builder()
                .id(found.getId())
                .clubType(found.getClubType())
                .clubName(found.getClubName())
                .clubIntro(found.getClubIntro())
                .clubImg(found.getClubImg())
                .advisorName(found.getAdvisorName())
                .advisorMajor(found.getAdvisorMajor())
                .advisorContact(found.getAdvisorContact())
                .regularMeeting(found.getRegularMeeting())
                .createdAt(found.getCreatedAt())
                .build();
    }

    public void setClubImg(String clubImg) {
        final String URL_PREFIX = "https://storage.googleapis.com/bidmarkit-bucket/";
        this.clubImg = URL_PREFIX + clubImg;
    }
}
