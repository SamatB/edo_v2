/**
 * Сервис для работы с Keycloak
 */

package org.example.service.impl;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.example.dto.EmployeeDto;
import org.example.feign.EmployeeFeignClient;
import org.example.service.KeycloakService;
import org.keycloak.KeycloakPrincipal;
import org.keycloak.KeycloakSecurityContext;
import org.keycloak.representations.IDToken;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@RequiredArgsConstructor
@NoArgsConstructor(force = true)
public class KeycloakServiceImpl implements KeycloakService {

    private static final Logger log = LoggerFactory.getLogger(KeycloakServiceImpl.class);
    private final EmployeeFeignClient employeeFeignClient;
    /**
     * Метод для получения работника по username из сессии Keycloak
     *
     * @param request - запрос.
     * @return объект DTO работника.
     */
    @Override
    public EmployeeDto getEmployeeFromSessionUsername(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if (session != null) {
            log.info("Outside if - getEmployeeFromSessionUsername");
            // Проверяем, есть ли KeycloakPrincipal в сессии
            KeycloakPrincipal<KeycloakSecurityContext> keycloakPrincipal =
                    (KeycloakPrincipal<KeycloakSecurityContext>) session
                            .getAttribute(KeycloakSecurityContext.class.getName());
            if (keycloakPrincipal != null) {
                // Получаем access token из KeycloakPrincipal
                IDToken idToken = keycloakPrincipal.getKeycloakSecurityContext().getIdToken();

                // Получаем username из access token
                String username = idToken.getPreferredUsername();

                log.info("Inside getEmployeeFromSessionUsername");
                // Получение Employee по username
                return employeeFeignClient.getByUsername(username);
            }
        }
        log.info("Outside getEmployeeFromSessionUsername");
        // Если сессия Keycloak отсутствует или KeycloakPrincipal отсутствует в сессии, возвращаем null
        return null;
    }
}

