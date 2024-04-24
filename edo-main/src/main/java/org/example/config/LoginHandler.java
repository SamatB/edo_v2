package org.example.config;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.service.EmployeeSessionService;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

@Slf4j
@RequiredArgsConstructor
@Component
public class
LoginHandler implements AuthenticationSuccessHandler {

    private final EmployeeSessionService employeeSessionService;

    /**
     * Сохраняет в redis сессию вошедшего пользователя.
     */
    @Override
    public void onAuthenticationSuccess(HttpServletRequest request,
                                        HttpServletResponse response,
                                        Authentication authentication) {
        String sessionId = request.getSession().getId();
        String username = authentication.getName();
        log.info("Вошел пользователь " + username + ", sessionId " + sessionId);
        employeeSessionService.saveEmployeeSession(sessionId, username);
    }
}
