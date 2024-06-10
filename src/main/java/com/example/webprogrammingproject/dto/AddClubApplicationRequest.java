package com.example.webprogrammingproject.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class AddClubApplicationRequest {
    private String clubType;
    private String clubName;
    private String advisorName;
    private String advisorMajor;
    private String advisorContact;
}
