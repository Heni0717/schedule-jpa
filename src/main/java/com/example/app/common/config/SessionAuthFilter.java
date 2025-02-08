package com.example.app.common.config;

import com.example.app.userinfo.auth.SessionStorage;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;

@RequiredArgsConstructor
public class SessionAuthFilter extends OncePerRequestFilter {

    private final SessionStorage sessionStorage;
    private final List<String> excludedPaths = List.of("/auth/login", "users/signup");

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String requestUri = request.getRequestURI();
        if (isExcludedPath(requestUri)) {
            filterChain.doFilter(request, response);
            return;
        }

        String sessionId = extractSessionIdFromCookies(request.getCookies());
        if (sessionId == null || sessionStorage.getSession(sessionId) == null) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.setContentType("text/plain; charset=UTF-8");
            response.getWriter().write("로그인 필요");
            return;
        }
        filterChain.doFilter(request, response);
    }

    private boolean isExcludedPath(String uri) {
        return excludedPaths.stream().anyMatch(uri::contains);
    }

    private String extractSessionIdFromCookies(Cookie[] cookies) {
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if ("SESSIONID".equals(cookie.getName())) {
                    return cookie.getValue();
                }
            }
        }
        return null;
    }
}
