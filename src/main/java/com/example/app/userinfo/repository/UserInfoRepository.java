package com.example.app.userinfo.repository;

import com.example.app.userinfo.entity.UserInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

public interface UserInfoRepository extends JpaRepository<UserInfo, Long> {

    default UserInfo findUserInfosByIdOrElseThrow(Long id){
        return findById(id).orElseThrow(()
                -> new ResponseStatusException(HttpStatus.NOT_FOUND, "존재하지 않는 사용자")
        );
    }

    Optional<UserInfo> findByEmail(String email);
}
