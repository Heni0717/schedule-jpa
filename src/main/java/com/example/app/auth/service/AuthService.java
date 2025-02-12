package com.example.app.auth.service;

import com.example.app.common.config.PasswordEncoder;
import com.example.app.common.config.SessionStorage;
import com.example.app.common.util.AuthUtil;
import com.example.app.schedule.repository.ScheduleRepository;
import com.example.app.userinfo.entity.UserInfo;
import com.example.app.userinfo.repository.UserInfoRepository;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserInfoRepository userInfoRepository;
    private final ScheduleRepository scheduleRepository;
    private final SessionStorage sessionStorage;
    private final PasswordEncoder passwordEncoder;

    public UserInfo login(String email, String password) {
        UserInfo userInfo = userInfoRepository.findByEmail(email)
                .orElseThrow(()-> new ResponseStatusException(HttpStatus.UNAUTHORIZED, "존재하지 않는 유저"));
        if(!passwordEncoder.matches(password,userInfo.getPassword())){
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "비밀번호 불일치");
        }
        return userInfo;
    }

    public boolean isOwner(Long resourceId, Long userId) {
        return scheduleRepository.findById(resourceId)
                .map(schedule -> schedule.getUserInfo().getId().equals(userId))
                .orElse(false);
    }

    public UserInfo getCurrentUserInfo(HttpServletRequest request){
        String sessionId = AuthUtil.extractSessionIdFromCookies(request.getCookies());
        if(sessionId == null){
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "세션 ID 존재하지 않음");
        }
        UserInfo userInfo = sessionStorage.getSession(sessionId);
        if(userInfo == null){
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "유효하지 않은 세션");
        }
        return userInfo;
    }

}
