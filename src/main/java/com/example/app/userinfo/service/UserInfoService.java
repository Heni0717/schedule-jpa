package com.example.app.userinfo.service;

import com.example.app.common.config.PasswordEncoder;
import com.example.app.schedule.service.ScheduleService;
import com.example.app.userinfo.dto.UserInfoResponseDto;
import com.example.app.userinfo.entity.UserInfo;
import com.example.app.userinfo.repository.UserInfoRepository;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
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
    private final PasswordEncoder passwordEncoder;
    private final ScheduleService scheduleService;

    @Transactional
    public UserInfoResponseDto signUp(String userName, String email, String password) {
        String encodedPassword = passwordEncoder.encode(password);
        UserInfo userInfo = new UserInfo(userName, email, encodedPassword);
        UserInfo newUser = userInfoRepository.save(userInfo);
        return new UserInfoResponseDto(newUser.getId(), newUser.getUserName(), newUser.getEmail());
    }

    @Transactional(readOnly = true)
    public List<UserInfoResponseDto> findAllUsers() {
        return userInfoRepository.findAll().stream().map(UserInfoResponseDto::toDto).toList();
    }

    @Transactional(readOnly = true)
    public UserInfoResponseDto findUserInfoById(Long id) {
        UserInfo findUser = userInfoRepository.findUserInfosByIdOrElseThrow(id);
        return new UserInfoResponseDto(findUser.getId(), findUser.getUserName(), findUser.getEmail());
    }

    @Transactional
    public UserInfoResponseDto updateUserInfoById(Long id, String userName, String oldPassword, String newPassword) {
        UserInfo userInfo = userInfoRepository.findUserInfosByIdOrElseThrow(id);
        if(!passwordEncoder.matches(oldPassword, userInfo.getPassword())){
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "비밀번호 불일치");
        }
        String encodedNewPassword = passwordEncoder.encode(newPassword);
        userInfo.updateUser(userName, encodedNewPassword);
        return new UserInfoResponseDto(userInfo.getId(), userInfo.getUserName(), userInfo.getEmail());
    }

    @Transactional
    public void deleteUserInfo(Long id, HttpServletRequest request) {
        scheduleService.deleteSchedulesByUserInfo(id);
        UserInfo userInfo = userInfoRepository.findUserInfosByIdOrElseThrow(id);
        userInfoRepository.delete(userInfo);
        HttpSession session = request.getSession(false);
        if(session!=null){
            UserInfo currentUser = (UserInfo) session.getAttribute("userInfo");
            if(currentUser!=null && currentUser.getId().equals(id)){
                session.invalidate();
            }
        }
    }
}
