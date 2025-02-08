package com.example.app.userinfo.auth;

import com.example.app.schedule.repository.ScheduleRepository;
import com.example.app.userinfo.entity.UserInfo;
import com.example.app.userinfo.repository.UserInfoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserInfoRepository userInfoRepository;
    private final ScheduleRepository scheduleRepository;

    public UserInfo login(String email, String password) {
        UserInfo userInfo = userInfoRepository.findByEmail(email)
                .orElseThrow(()-> new ResponseStatusException(HttpStatus.UNAUTHORIZED, "존재하지 않는 유저"));
        if(!userInfo.getPassword().equals(password)){
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "비밀번호 불일치");
        }
        return userInfo;
    }

    public boolean isOwner(Long resourceId, Long userId) {
        return scheduleRepository.findById(resourceId)
                .map(schedule -> schedule.getUserInfo().getId().equals(userId))
                .orElse(false);
    }
}
