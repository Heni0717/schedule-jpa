package com.example.app.comment.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class CommentRequestDto {

    private final Long scheduleId;
    private final String content;

}
