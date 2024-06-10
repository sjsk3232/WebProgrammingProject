package com.example.webprogrammingproject.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class UpdateClubMemberRequest {
    private Long clubId;
    private String memberId;
    private Boolean isMaster;
    private String position;
    private String state;
}
