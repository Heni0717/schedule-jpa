package com.example.app.auth.controller;

import com.example.app.auth.dto.LoginRequestDto;
import com.example.app.auth.service.AuthService;
import com.example.app.userinfo.entity.UserInfo;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
public class AuthController {

    private final AuthService authService;

    @PostMapping("/login")
    public ResponseEntity<?> login(@Valid @RequestBody LoginRequestDto requestDto,HttpServletRequest request, HttpServletResponse response){
        UserInfo userInfo = authService.login(requestDto.getEmail(), requestDto.getPassword());
        // 생성되는 쿠키, 세션 정보는 로그인 검증, 유저 검증 필터에 사용됨
        HttpSession session = request.getSession(true);
        session.setAttribute("userInfo", userInfo);
        Cookie sessionCookie = new Cookie("SESSIONID", session.getId());
        sessionCookie.setHttpOnly(true);
        sessionCookie.setMaxAge(3600);
        sessionCookie.setPath("/");
        response.addCookie(sessionCookie);
        return ResponseEntity.ok("로그인 성공");
    }

    @PostMapping("/logout")
    public ResponseEntity<?> logout(@CookieValue(value = "SESSIONID", required = false) String sessionId, HttpServletRequest request, HttpServletResponse response) {
        // 로그인 사용자 정보를 브라우저에 남기지 않기 위해 로그아웃시 세션과 쿠키 만료
        HttpSession session = request.getSession(false);
        if(session!= null){
            session.invalidate();
        }
        Cookie sessionCookie = new Cookie("SESSIONID", null);
        sessionCookie.setHttpOnly(true);
        sessionCookie.setMaxAge(0);
        sessionCookie.setPath("/");
        response.addCookie(sessionCookie);
        return ResponseEntity.ok("로그아웃 성공");
    }
}
