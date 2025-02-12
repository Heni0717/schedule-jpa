package com.example.app.schedule.dto;

import com.example.app.schedule.entity.Schedule;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.time.format.DateTimeFormatter;

@Getter
@RequiredArgsConstructor
public class ScheduleResponseDto {

    private final Long id;
    private final String title;
    private final String content;
    private final int commentCount;
    private final String userName;
    private final String createdAt;
    private final String updatedAt;

    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    public ScheduleResponseDto(Schedule schedule) {
        this.id = schedule.getId();
        this.title = schedule.getTitle();
        this.content = schedule.getContent();
        this.commentCount = schedule.getComments().size();
        this.userName = schedule.getUserInfo().getUserName();
        this.createdAt = schedule.getCreatedAt().format(FORMATTER);
        this.updatedAt = schedule.getUpdatedAt().format(FORMATTER);
    }

}
