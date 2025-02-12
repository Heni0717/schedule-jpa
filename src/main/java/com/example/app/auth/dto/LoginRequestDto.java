package com.example.app.auth.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class LoginRequestDto {

    @NotBlank(message = "이메일 입력바람")
    @Email(message = "이메일 형식에 맞게 작성")
    private final String email;

    @NotBlank(message = "비밀번호 입력바람")
    private final String password;
}
