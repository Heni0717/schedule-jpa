package com.example.app.comment.service;

import com.example.app.comment.dto.CommentResponseDto;
import com.example.app.comment.entity.Comment;
import com.example.app.comment.repository.CommentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CommentService {

    private final CommentRepository commentRepository;

    @Transactional
    public void createComment(String content) {
        Comment newComment = new Comment(content);
        commentRepository.save(newComment);
    }

    @Transactional(readOnly = true)
    public List<CommentResponseDto> findAllComments(){
        return commentRepository.findAll().stream().map(CommentResponseDto::toDto).toList();
    }

    @Transactional(readOnly = true)
    public CommentResponseDto findCommentById(Long id){
        Comment findComment = commentRepository.findById(id)
                .orElseThrow(() -> new IllegalStateException("존재하지 않는 댓글"));
        return new CommentResponseDto(findComment.getId(), findComment.getContent());
    }

    @Transactional
    public CommentResponseDto updateCommentById(Long id, String newContent){
        Comment comment = commentRepository.findById(id)
                .orElseThrow(() -> new IllegalStateException("존재하지 않는 댓글"));
        comment.updateComment(newContent);
        commentRepository.save(comment);
        return new CommentResponseDto(comment.getId(), comment.getContent());
    }

    @Transactional
    public void deleteCommentById(Long id){
        commentRepository.deleteCommentById(id);
    }
}
