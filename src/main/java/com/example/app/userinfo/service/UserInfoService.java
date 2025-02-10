package com.example.app.userinfo.service;

import com.example.app.userinfo.auth.SessionStorage;
import com.example.app.userinfo.dto.UserInfoResponseDto;
import com.example.app.userinfo.entity.UserInfo;
import com.example.app.userinfo.repository.UserInfoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserInfoService {

    private final UserInfoRepository userInfoRepository;
    private final SessionStorage sessionStorage;

    public UserInfoResponseDto signUp(String userName, String email, String password) {
        UserInfo userInfo = new UserInfo(userName, email, password);
        UserInfo newUser = userInfoRepository.save(userInfo);
        return new UserInfoResponseDto(newUser.getId(), newUser.getUserName(), newUser.getEmail());
    }

    public List<UserInfoResponseDto> findAllUsers() {
        return userInfoRepository.findAll().stream().map(UserInfoResponseDto::toDto).toList();
    }

    public UserInfoResponseDto findUserInfoById(Long id) {
        UserInfo findUser = userInfoRepository.findUserInfosByIdOrElseThrow(id);
        return new UserInfoResponseDto(findUser.getId(), findUser.getUserName(), findUser.getEmail());
    }

    @Transactional
    public UserInfoResponseDto updateUserInfoById(Long id, String userName, String oldPassword, String newPassword) {
        UserInfo userInfo = userInfoRepository.findUserInfosByIdOrElseThrow(id);
        if(!userInfo.getPassword().equals(oldPassword)){
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "비밀번호 불일치");
        }
        userInfo.updateUser(userName, newPassword);
        userInfoRepository.save(userInfo);
        return new UserInfoResponseDto(userInfo.getId(), userInfo.getUserName(), userInfo.getEmail());
    }

    public void deleteUserInfo(Long id) {
        UserInfo userInfo = userInfoRepository.findUserInfosByIdOrElseThrow(id);
        userInfoRepository.delete(userInfo);
        sessionStorage.removeSessionByUserId(id);
    }
}
