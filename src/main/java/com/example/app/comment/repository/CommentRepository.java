package com.example.app.comment.repository;

import com.example.app.comment.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public interface CommentRepository extends JpaRepository<Comment, Long> {

    default Comment findByIdOrElseThrow(Long id){
        return findById(id).orElseThrow(()
                -> new ResponseStatusException(HttpStatus.NOT_FOUND, "존재하지 않는 댓글")
        );
    }

    void deleteCommentById(Long id);
}
