package com.example.app.userinfo.auth;

import com.example.app.userinfo.entity.UserInfo;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
public class AuthController {

    private final AuthService authService;
    private final SessionStorage sessionStorage;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequestDto requestDto, HttpServletResponse response){
        UserInfo userInfo = authService.login(requestDto.getEmail(), requestDto.getPassword());
        String sessionId = sessionStorage.createSession(userInfo);
        Cookie sessionCookie = new Cookie("SESSIONID", sessionId);
        sessionCookie.setHttpOnly(true);
        sessionCookie.setMaxAge(3600);
        sessionCookie.setPath("/");
        response.addCookie(sessionCookie);
        return ResponseEntity.ok("로그인 성공");
    }

    @PostMapping("/logout")
    public ResponseEntity<?> logout(@CookieValue(value = "SESSIONID", required = false) String sessionId, HttpServletResponse response) {
        if (sessionId != null) {
            sessionStorage.removeSession(sessionId);
        }
        Cookie sessionCookie = new Cookie("SESSIONID", null);
        sessionCookie.setHttpOnly(true);
        sessionCookie.setMaxAge(0);
        response.addCookie(sessionCookie);
        return ResponseEntity.ok("로그아웃 성공");
    }



}
