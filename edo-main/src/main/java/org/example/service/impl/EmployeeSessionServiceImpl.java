package org.example.service.impl;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.service.EmployeeSessionService;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * Класс для работы с Employee и redis
 */
@Slf4j
@RequiredArgsConstructor
@Component
public class EmployeeSessionServiceImpl implements EmployeeSessionService {

    private static final String EMPLOYEE_SESSION_KEY = "employee_session";

    private final RedisTemplate<String, String> redisTemplate;
    private HashOperations<String, String, String> hashOperations;

    @PostConstruct
    private void init() {
        hashOperations = redisTemplate.opsForHash();
    }

    @Override
    public void saveEmployeeSession(String sessionId, String username) {
        log.info("Сохранение в redis сессии " + sessionId + " пользователя " + username);
        hashOperations.put(EMPLOYEE_SESSION_KEY, sessionId, username);
    }

    @Override
    public void deleteEmployeeSession(String sessionId) {
        log.info("Удаление сессии " + sessionId + " из redis");
        hashOperations.delete(EMPLOYEE_SESSION_KEY, sessionId);
    }

    @Override
    public int getOnlineUsersNumber() {
        log.info("Подсчет пользователей в redis");
        Map<String, String> allSessions = hashOperations.entries(EMPLOYEE_SESSION_KEY);
        Set<String> allSessionIds = new HashSet<>(allSessions.values());
        return allSessionIds.size();
    }
}
