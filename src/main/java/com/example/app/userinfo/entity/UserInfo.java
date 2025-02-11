package com.example.app.userinfo.entity;


import com.example.app.common.model.BaseEntity;
import com.example.app.schedule.entity.Schedule;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Table(name = "user_info")
@Getter
@NoArgsConstructor
public class UserInfo extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String userName;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String password;

    @OneToMany(mappedBy = "userInfo", cascade = CascadeType.REMOVE, orphanRemoval = true)
    private List<Schedule> schedules;

    public UserInfo(String userName, String email, String password){
        this.userName = userName;
        this.email = email;
        this.password = password;
    }

    public void updateUser(String userName, String password){
        this.userName = userName;
        this.password = password;
    }

}
