package com.example.app.schedule.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class ScheduleRequestDto {

    @NotBlank(message = "제목을 입력하라")
    @Size(max = 50, message = "제목은 최대 50자까지만 입력 가능")
    private final String title;

    @NotBlank(message = "내용을 입력하라")
    private final String content;

}
