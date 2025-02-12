package com.example.app.comment.entity;

import com.example.app.common.model.BaseEntity;
import com.example.app.schedule.entity.Schedule;
import com.example.app.userinfo.entity.UserInfo;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Table(name = "comment")
@Entity
@Getter
@NoArgsConstructor
public class Comment extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String content;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private UserInfo userInfo;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "schedule_id", nullable = false)
    private Schedule schedule;

    public Comment(String content, UserInfo userInfo, Schedule schedule){
        this.content = content;
        this.userInfo = userInfo;
        this.schedule = schedule;
    }

    public void updateComment(String content){
        this.content = content;
    }
}
