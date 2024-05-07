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
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class KeycloakServiceImpl implements KeycloakService {

    private final EmployeeFeignClient employeeFeignClient;

    /**
     * Метод для получения работника по username из сессии Keycloak
     *
     * @return объект DTO работника.
     */
    @Override
    public EmployeeDto getEmployeeFromSessionUsername() {
        // Проверяем, есть ли KeycloakPrincipal в сессии
        var keycloakPrincipal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (keycloakPrincipal != null) {
            var username = "";
            if (keycloakPrincipal instanceof KeycloakPrincipal) {
                // Получаем access token из KeycloakPrincipal
                IDToken idToken =  ((KeycloakPrincipal<KeycloakSecurityContext>) keycloakPrincipal)
                        .getKeycloakSecurityContext().getIdToken();

                // Получаем username из access token
                username = idToken.getPreferredUsername();
            } else if(keycloakPrincipal instanceof Jwt) {
                username = ((Jwt) keycloakPrincipal).getClaim("preferred_username");
            }

            // Получение Employee по username
            return employeeFeignClient.getByUsername(username);
        }

        // Если сессия Keycloak отсутствует или KeycloakPrincipal отсутствует в сессии, возвращаем null
        return null;
    }
}

