package com.example.webprogrammingproject.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class LoginResponse {
    String name, refreshToken, accessToken, role;
}
