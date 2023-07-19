/**
 * Сервис для работы с Keycloak
 */

package org.example.service;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.example.dto.EmployeeDto;
import org.example.feign.EmployeeFeignClient;
import org.keycloak.KeycloakPrincipal;
import org.keycloak.KeycloakSecurityContext;
import org.keycloak.representations.IDToken;

@RequiredArgsConstructor
public class KeycloakService {

    private final EmployeeFeignClient employeeFeignClient;
    /**
     * Метод для получения работника по username из сессии Keycloak
     *
     * @param request - запрос.
     * @return объект DTO работника.
     */
    public EmployeeDto getEmployeeFromSessionUsername(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if (session != null) {
            // Проверяем, есть ли KeycloakPrincipal в сессии
            KeycloakPrincipal<KeycloakSecurityContext> keycloakPrincipal =
                    (KeycloakPrincipal<KeycloakSecurityContext>) session
                            .getAttribute(KeycloakSecurityContext.class.getName());
            if (keycloakPrincipal != null) {
                // Получаем access token из KeycloakPrincipal
                IDToken idToken = keycloakPrincipal.getKeycloakSecurityContext().getIdToken();

                // Получаем username из access token
                String username = idToken.getPreferredUsername();

                // Получение Employee по username
                return employeeFeignClient.getByUsername(username);
            }
        }

        // Если сессия Keycloak отсутствует или KeycloakPrincipal отсутствует в сессии, возвращаем null
        return null;
    }
}

