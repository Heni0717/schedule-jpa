package com.example.app.common.config;

import com.example.app.userinfo.auth.AuthService;
import com.example.app.userinfo.auth.SessionStorage;
import com.example.app.userinfo.entity.UserInfo;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
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
            Long resourceId = Long.valueOf(extractResourceId(request.getRequestURI()));

            Long currentUserId = getCurrentUserId(request);

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

    /**
     * URI에서 리소스 ID 추출
     */
    private String extractResourceId(String uri) {
        String[] segments = uri.split("/");
        return segments[segments.length - 1];
    }

    /**
     * 쿠키에서 세션 ID 추출 및 현재 유저 ID 가져오기
     */
    private Long getCurrentUserId(HttpServletRequest request) {
        String sessionId = extractSessionIdFromCookies(request.getCookies());
        if (sessionId == null) {
            throw new IllegalArgumentException("세션 ID가 존재하지 않음");
        }

        UserInfo userInfo = sessionStorage.getSession(sessionId);
        if (userInfo == null) {
            throw new IllegalArgumentException("유효하지 않은 세션");
        }
        return userInfo.getId();
    }

    /**
     * 쿠키에서 세션 ID 추출
     */
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
