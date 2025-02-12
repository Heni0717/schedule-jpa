package com.example.app.common.config;

import com.example.app.auth.service.AuthService;
import com.example.app.common.util.AuthUtil;
import com.example.app.userinfo.entity.UserInfo;
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
        String method = request.getMethod();
        String requestUri = request.getRequestURI();
        if(method.equals("PUT") || method.equals("DELETE")){
            Long resourceId = Long.valueOf(AuthUtil.extractResourceId(request.getRequestURI()));
            UserInfo currentUser = authService.getCurrentUserInfo(request);
            String resourceType = requestUri.startsWith("/schedules/") ? "schedule" :
                            requestUri.startsWith("/users/") ? "user" :
                            requestUri.startsWith("/comments/") ? "comment" : null;
            if (resourceType == null || !authService.isOwner(resourceId, currentUser.getId(), resourceType)) {
                response.setStatus(HttpServletResponse.SC_FORBIDDEN);
                response.setContentType("text/plain; charset=UTF-8");
                response.getWriter().write("접근 권한 없음");
                return;
            }
        }
        filterChain.doFilter(request, response);
    }
}
