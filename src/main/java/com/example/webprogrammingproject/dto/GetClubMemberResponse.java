package com.example.webprogrammingproject.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class GetClubMemberResponse {
    private String position;
    private String memberId;
    private String name;
    private String state;
}
