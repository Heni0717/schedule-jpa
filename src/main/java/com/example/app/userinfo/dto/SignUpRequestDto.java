package com.example.app.userinfo.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class SignUpRequestDto {

    private final String userName;
    private final String email;
    private final String password;

}
