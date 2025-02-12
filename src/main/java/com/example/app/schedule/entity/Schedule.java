package com.example.app.schedule.entity;


import com.example.app.common.model.BaseEntity;
import com.example.app.userinfo.entity.UserInfo;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "schedule")
@Getter
@NoArgsConstructor
public class Schedule extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;

    @Column(columnDefinition = "text")
    private String content;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private UserInfo userInfo;

    public Schedule(String title, String content, UserInfo userInfo){
        this.title = title;
        this.content = content;
        this.userInfo = userInfo;
    }

    public void updateSchedule(String title, String content){
        this.title = title;
        this.content = content;
    }

}
