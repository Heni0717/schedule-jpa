package com.example.app.userinfo.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class UserInfoResponseDto {

    private final String username;
    private final String email;

}