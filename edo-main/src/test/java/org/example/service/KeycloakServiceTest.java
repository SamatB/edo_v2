package org.example.service;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.example.dto.EmployeeDto;
import org.example.feign.EmployeeFeignClient;
import org.example.service.impl.KeycloakServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.keycloak.KeycloakPrincipal;
import org.keycloak.KeycloakSecurityContext;
import org.keycloak.representations.IDToken;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class KeycloakServiceTest {

    @Mock
    private EmployeeFeignClient employeeFeignClient;

    @Mock
    private HttpServletRequest request;

    @Mock
    private HttpSession session;

    @Mock
    private KeycloakPrincipal<KeycloakSecurityContext> keycloakPrincipal;

    @Mock
    private KeycloakSecurityContext keycloakSecurityContext;

    @Mock
    private IDToken idToken;

    private KeycloakService keycloakService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        keycloakService = new KeycloakServiceImpl(employeeFeignClient);
    }

    @Test
    void testGetEmployeeFromSessionUsername() {
        String username = "testUser";
        EmployeeDto employeeDto = new EmployeeDto();
        employeeDto.setUsername(username);

        when(request.getSession(false)).thenReturn(session);
        when(session.getAttribute(KeycloakSecurityContext.class.getName())).thenReturn(keycloakPrincipal);
        when(keycloakPrincipal.getKeycloakSecurityContext()).thenReturn(keycloakSecurityContext);
        when(keycloakSecurityContext.getIdToken()).thenReturn(idToken);
        when(idToken.getPreferredUsername()).thenReturn(username);
        when(employeeFeignClient.getByUsername(username)).thenReturn(employeeDto);

        EmployeeDto result = keycloakService.getEmployeeFromSessionUsername(request);

        assertEquals(employeeDto, result);
        verify(employeeFeignClient, times(1)).getByUsername(username);
    }
}
