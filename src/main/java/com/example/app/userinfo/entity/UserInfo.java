package com.example.app.userinfo.entity;


import com.example.app.common.entity.BaseEntity;
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
    private String userName;

    @Column(nullable = false, unique = true)
//    @Email(message = "이메일 형식에 맞게 작성")
    private String email;

    @Column(nullable = false)
    private String password;

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
