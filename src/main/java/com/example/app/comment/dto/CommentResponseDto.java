package com.example.app.comment.dto;

import com.example.app.comment.entity.Comment;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class CommentResponseDto {

    private final Long id;

    private final String content;

    public static CommentResponseDto toDto(Comment comment){
        return new CommentResponseDto(comment.getId(), comment.getContent());
    }

}
