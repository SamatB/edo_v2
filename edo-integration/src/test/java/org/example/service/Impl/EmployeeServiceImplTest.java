package org.example.service.Impl;

import org.example.feign.EmployeeFeignClient;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("EmployeeServiceImpl test")
class EmployeeServiceImplTest {
    @Mock
    private EmployeeFeignClient employeeFeignClient;

    @InjectMocks
    private EmployeeServiceImpl employeeService;

    @Test
    @DisplayName("Should throw an exception when null is passed as ids")
    void getEmailByIdWhenNullIsPassedAsIdsThenThrowException() {
        assertThrows(IllegalArgumentException.class, () -> employeeService.getEmailsByIds(null));
    }

    @Test
    @DisplayName("Should return an empty collection when no ids are provided")
    void getEmailByIdWhenNoIdsAreProvided() {
        Collection<Long> ids = Collections.emptyList();

        Collection<String> result = employeeService.getEmailsByIds(ids);

        assertTrue(result.isEmpty());
        verifyNoInteractions(employeeFeignClient);
    }

    @Test
    @DisplayName("Should return a collection of emails when valid ids are provided")
    void getEmailByIdWhenValidIdsAreProvided() {
        Collection<Long> ids = Arrays.asList(1L, 2L, 3L);
        Collection<String> expectedEmails = Arrays.asList("john.doe@example.com", "jane.smith@example.com", "bob.johnson@example.com");

        when(employeeFeignClient.getEmailsByIds(ids)).thenReturn(expectedEmails);

        Collection<String> actualEmails = employeeService.getEmailsByIds(ids);

        assertEquals(expectedEmails.size(), actualEmails.size());
        assertTrue(actualEmails.containsAll(expectedEmails));
        verify(employeeFeignClient, times(1)).getEmailsByIds(ids);
    }

}