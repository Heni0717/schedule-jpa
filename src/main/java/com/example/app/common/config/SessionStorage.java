package com.example.app.common.config;

import com.example.app.userinfo.entity.UserInfo;
import org.springframework.stereotype.Component;

import java.util.Iterator;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class SessionStorage {

    private final ConcurrentHashMap<String, UserInfo> sessionMap = new ConcurrentHashMap<>();

    public String createSession(UserInfo userInfo) {
        String sessionId = UUID.randomUUID().toString();
        sessionMap.put(sessionId, userInfo);
        return sessionId;
    }

    public UserInfo getSession(String sessionId) {
        return sessionMap.get(sessionId);
    }

    public void removeSession(String sessionId) {
        sessionMap.remove(sessionId);
    }

    public void removeSessionByUserId(Long userId) {
        Iterator<Map.Entry<String, UserInfo>> iterator = sessionMap.entrySet().iterator();
        while (iterator.hasNext()) {
            Map.Entry<String, UserInfo> entry = iterator.next();
            if (entry.getValue().getId().equals(userId)) {
                iterator.remove();
            }
        }
    }
}
