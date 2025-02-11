package com.example.app.comment.controller;

import com.example.app.comment.dto.CommentRequestDto;
import com.example.app.comment.dto.CommentResponseDto;
import com.example.app.comment.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/comments")
@RequiredArgsConstructor
public class CommentController {

    private final CommentService commentService;

    @PostMapping
    public ResponseEntity<Void> createComment(@RequestBody CommentRequestDto requestDto){
        commentService.createComment(requestDto.getContent());
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CommentResponseDto> findCommentById(@PathVariable Long id){
        CommentResponseDto commentById = commentService.findCommentById(id);
        return new ResponseEntity<>(commentById, HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<List<CommentResponseDto>> findAllComments(){
        List<CommentResponseDto> allComments = commentService.findAllComments();
        return new ResponseEntity<>(allComments, HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<CommentResponseDto> updateComment(@PathVariable Long id, @RequestBody CommentRequestDto requestDto){
        CommentResponseDto commentResponseDto = commentService.updateCommentById(id, requestDto.getContent());
        return new ResponseEntity<>(commentResponseDto, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteComment(@PathVariable Long id){
        commentService.deleteCommentById(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
