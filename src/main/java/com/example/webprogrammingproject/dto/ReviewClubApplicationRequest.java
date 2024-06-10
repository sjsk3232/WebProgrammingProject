package com.example.webprogrammingproject.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class ReviewClubApplicationRequest {
    private Long clubApplicationId;
    private String result;
    private String rejectionReason;
}
