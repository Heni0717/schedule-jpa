package com.example.app.userinfo.dto;

import com.example.app.userinfo.entity.UserInfo;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class UserInfoResponseDto {

    private final Long id;
    private final String userName;
    private final String email;

    public static UserInfoResponseDto toDto(UserInfo userInfo){
        return new UserInfoResponseDto(userInfo.getId(), userInfo.getUserName(), userInfo.getEmail());
    }
}