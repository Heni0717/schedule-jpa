package com.example.app.userinfo.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class UpdateUserInfoRequestDto {

    private final String userName;
    private final String oldPassword;
    private final String newPassword;

}
