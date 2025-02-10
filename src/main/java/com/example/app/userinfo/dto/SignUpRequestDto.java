package com.example.app.userinfo.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class SignUpRequestDto {

    @NotBlank(message = "이름 입력바람")
    @Size(min = 2, message = "이름은 최소 2글자 이상")
    private final String userName;

    @Email(message = "이메일 형식에 맞게 입력")
    @NotBlank(message = "이메일 입력바람")
    private final String email;

    @NotBlank(message = "비밀번호 입력바람")
    private final String password;

}
