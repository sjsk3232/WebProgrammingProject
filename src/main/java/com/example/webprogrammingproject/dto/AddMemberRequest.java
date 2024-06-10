package com.example.webprogrammingproject.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class AddMemberRequest {
    private String email;
    private String password;
    private String name;
    private LocalDate birthday;
    private String major;
    private String studentNumber;
    private String phoneNumber;
}
