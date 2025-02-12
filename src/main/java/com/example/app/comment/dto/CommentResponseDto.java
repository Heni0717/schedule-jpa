package com.example.app.comment.dto;

import com.example.app.comment.entity.Comment;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.time.format.DateTimeFormatter;

@Getter
@RequiredArgsConstructor
public class CommentResponseDto {

    private final Long id;
    private final String content;
    private final String userName;
    private final String createdAt;
    private final String updatedAt;

    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    public CommentResponseDto(Comment comment) {
        this.id = comment.getId();
        this.content = comment.getContent();
        this.userName = comment.getUserInfo().getUserName();
        this.createdAt = comment.getCreatedAt().format(FORMATTER);
        this.updatedAt = comment.getUpdatedAt().format(FORMATTER);
    }

    public static CommentResponseDto toDto(Comment comment){
        return new CommentResponseDto(
                comment.getId(),
                comment.getContent(),
                comment.getUserInfo().getUserName(),
                comment.getCreatedAt().format(FORMATTER),
                comment.getUpdatedAt().format(FORMATTER));
    }

}
