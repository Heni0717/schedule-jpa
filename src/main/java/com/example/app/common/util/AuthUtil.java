package com.example.app.common.util;

import java.util.List;

public class AuthUtil {

    public static String extractResourceId(String uri) {
        String[] segments = uri.split("/");
        return segments[segments.length - 1];
    }

    public static boolean isExcludedPath(String uri, List<String> excludedPaths) {
        return excludedPaths.stream().anyMatch(uri::contains);
    }

}
