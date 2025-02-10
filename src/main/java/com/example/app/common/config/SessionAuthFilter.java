package com.example.app.common.config;

import com.example.app.common.util.AuthUtil;
import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

import java.io.IOException;
import java.util.List;

@RequiredArgsConstructor
public class SessionAuthFilter implements Filter {

    private final SessionStorage sessionStorage;
    private final List<String> excludedPaths = List.of("/auth/login", "users/signup");

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        String requestUri = request.getRequestURI();

        if (AuthUtil.isExcludedPath(requestUri, excludedPaths)) {
            filterChain.doFilter(request, response);
            return;
        }

        String sessionId = AuthUtil.extractSessionIdFromCookies(request.getCookies());
        if (sessionId == null || sessionStorage.getSession(sessionId) == null) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.setContentType("text/plain; charset=UTF-8");
            response.getWriter().write("로그인 필요");
            return;
        }
        filterChain.doFilter(request, response);
    }


}
