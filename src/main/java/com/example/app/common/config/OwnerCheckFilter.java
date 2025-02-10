package com.example.app.common.config;

import com.example.app.common.util.AuthUtil;
import com.example.app.userinfo.auth.AuthService;
import com.example.app.userinfo.auth.SessionStorage;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@RequiredArgsConstructor
public class OwnerCheckFilter extends OncePerRequestFilter {

    private final AuthService authService;
    private final SessionStorage sessionStorage;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

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
