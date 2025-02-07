package com.example.app.userinfo.auth;

import com.example.app.userinfo.entity.UserInfo;
import com.example.app.userinfo.repository.UserInfoRepository;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
@RequiredArgsConstructor
public class LoginService {

    private final UserInfoRepository userInfoRepository;
    private final SessionController sessionController;

    public String login(String email, String password, HttpServletRequest request) {
        UserInfo userInfo = userInfoRepository.findByEmail(email)
                .orElseThrow(()-> new ResponseStatusException(HttpStatus.UNAUTHORIZED, "존재하지 않는 유저"));
        if(!userInfo.getPassword().equals(password)){
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "비밀번호 불일치");
        }
        sessionController.createSession(userInfo.getId(), request);
        return "로그인 성공";
    }
}
