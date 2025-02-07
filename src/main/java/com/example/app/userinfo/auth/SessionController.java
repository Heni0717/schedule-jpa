package com.example.app.userinfo.auth;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Component;

@Component
public class SessionController {

    private static final String SESSION_KEY = "USER_SESSION";
    public void createSession(Long id, HttpServletRequest request) {
        HttpSession session = request.getSession();
        session.setAttribute(SESSION_KEY, id);
    }
}
