package com.example.app.comment.service;

import com.example.app.auth.service.AuthService;
import com.example.app.comment.dto.CommentRequestDto;
import com.example.app.comment.dto.CommentResponseDto;
import com.example.app.comment.entity.Comment;
import com.example.app.comment.repository.CommentRepository;
import com.example.app.schedule.entity.Schedule;
import com.example.app.schedule.repository.ScheduleRepository;
import com.example.app.userinfo.entity.UserInfo;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CommentService {

    private final CommentRepository commentRepository;
    private final ScheduleRepository scheduleRepository;
    private final AuthService authService;

    @Transactional
    public void createComment(HttpServletRequest request, CommentRequestDto requestDto) {
        UserInfo userInfo = authService.getCurrentUserInfo(request);
        Schedule schedule = scheduleRepository.findByIdOrElseThrow(requestDto.getScheduleId());
        Comment newComment = new Comment(requestDto.getContent(), userInfo, schedule);
        commentRepository.save(newComment);
    }

    @Transactional(readOnly = true)
    public List<CommentResponseDto> findAllComments(){
        return commentRepository.findAll().stream().map(CommentResponseDto::toDto).toList();
    }

    @Transactional(readOnly = true)
    public CommentResponseDto findCommentById(Long id){
        Comment findComment = commentRepository.findByIdOrElseThrow(id);
        return new CommentResponseDto(findComment);
    }

    @Transactional
    public CommentResponseDto updateCommentById(Long id, String newContent){
        Comment comment = commentRepository.findByIdOrElseThrow(id);
        comment.updateComment(newContent);
        commentRepository.save(comment);
        return new CommentResponseDto(comment);
    }

    @Transactional
    public void deleteCommentById(Long id){
        commentRepository.deleteCommentById(id);
    }
}
