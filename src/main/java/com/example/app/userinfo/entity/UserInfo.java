package com.example.app.userinfo.entity;


import com.example.app.common.commonentity.BaseEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "user_info")
@Getter
@NoArgsConstructor
public class UserInfo extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String username;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String password;

    public UserInfo(String username, String email, String password){
        this.username = username;
        this.email = email;
        this.password = password;
    }

    public void updateUser(String username, String password){
        this.username = username;
        this.password = password;
    }

}
