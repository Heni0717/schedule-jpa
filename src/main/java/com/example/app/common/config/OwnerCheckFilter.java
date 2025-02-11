package com.example.app.common.config;

import com.example.app.common.util.AuthUtil;
import com.example.app.auth.service.AuthService;
import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

import java.io.IOException;

@RequiredArgsConstructor
public class OwnerCheckFilter implements Filter {

    private final AuthService authService;

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        try {
            Long resourceId = Long.valueOf(AuthUtil.extractResourceId(request.getRequestURI()));
            Long currentUserId = authService.getCurrentUserId(request);
            boolean isOwner = authService.isOwner(resourceId, currentUserId);
            if (!isOwner) {
                response.setStatus(HttpServletResponse.SC_FORBIDDEN);
                response.getWriter().write("접근 권한 없음");
                return;
            }
            filterChain.doFilter(request, response);
        } catch (NumberFormatException e) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            response.getWriter().write("잘못된 리소스 ID 형식");
        } catch (IllegalArgumentException e) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.getWriter().write(e.getMessage());
        }

    }

}
