package com.example.app.common.util;

import com.example.app.userinfo.entity.UserInfo;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;

import java.util.List;

public class AuthUtil {

    public static String extractResourceId(String uri) {
        String[] segments = uri.split("/");
        return segments[segments.length - 1];
    }

    public static String extractSessionIdFromCookies(Cookie[] cookies) {
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if ("SESSIONID".equals(cookie.getName())) {
                    return cookie.getValue();
                }
            }
        }
        return null;
    }

    public static boolean isExcludedPath(String uri, List<String> excludedPaths) {
        return excludedPaths.stream().anyMatch(uri::contains);
    }

}
