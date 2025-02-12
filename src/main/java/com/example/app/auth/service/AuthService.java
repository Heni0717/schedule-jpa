package com.example.app.auth.service;

import com.example.app.comment.repository.CommentRepository;
import com.example.app.common.config.PasswordEncoder;
import com.example.app.schedule.repository.ScheduleRepository;
import com.example.app.userinfo.entity.UserInfo;
import com.example.app.userinfo.repository.UserInfoRepository;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserInfoRepository userInfoRepository;
    private final ScheduleRepository scheduleRepository;
    private final PasswordEncoder passwordEncoder;
    private final CommentRepository commentRepository;

    public UserInfo login(String email, String password) {
        UserInfo userInfo = userInfoRepository.findByEmail(email)
                .orElseThrow(()-> new ResponseStatusException(HttpStatus.UNAUTHORIZED, "존재하지 않는 유저"));
        if(!passwordEncoder.matches(password,userInfo.getPassword())){
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "비밀번호 불일치");
        }
        return userInfo;
    }

    public boolean isOwner(Long resourceId, Long userId, String resourceType) {
        if ("schedule".equals(resourceType)) {
            return scheduleRepository.findById(resourceId)
                    .map(schedule -> schedule.getUserInfo().getId().equals(userId))
                    .orElse(false);
        }
        if ("user".equals(resourceType)) {
            return userInfoRepository.findById(resourceId)
                    .map(user -> user.getId().equals(userId))
                    .orElse(false);
        }
        if("comment".equals(resourceType)){
            return commentRepository.findById(resourceId)
                    .map(comment -> comment.getUserInfo().getId().equals(userId))
                    .orElse(false);
        }
        return false;
    }



    public UserInfo getCurrentUserInfo(HttpServletRequest request){
        HttpSession session = request.getSession(false);
        if(session == null){
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "세션 없음");
        }
        UserInfo userInfo = (UserInfo) session.getAttribute("userInfo");
        if(userInfo == null){
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "유효하지 않은 세션");
        }
        return userInfo;
    }

}
