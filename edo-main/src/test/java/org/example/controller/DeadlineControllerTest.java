package org.example.controller;
import org.example.dto.DeadlineDto;
import org.example.feign.DeadlineFeignClient;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

/**
 * Тесты для класса DeadlineController.
 */
@DisplayName("Тестирование контроллера для работы с дедлайном")
public class DeadlineControllerTest {

    @Mock
    private DeadlineFeignClient deadlineFeignClient;

    @InjectMocks
    private DeadlineController deadlineController;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    /**
     * Тест для метода setOrUpdateDeadline.
     */
    @Test
    public void testSetOrUpdateDeadline() {
        DeadlineDto deadlineDto = new DeadlineDto();
        when(deadlineFeignClient.setOrUpdateDeadline(anyLong(), any(DeadlineDto.class))).thenReturn(deadlineDto);

        ResponseEntity<DeadlineDto> response = deadlineController.setOrUpdateDeadline(1L, deadlineDto);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(deadlineDto, response.getBody());
    }

    /**
     * Тест для метода setOrUpdateDeadline с ошибкой.
     */
    @Test
    public void testUpdateResolutionWithError() {
        when(deadlineFeignClient.setOrUpdateDeadline(anyLong(), any(DeadlineDto.class))).thenThrow();

        ResponseEntity<DeadlineDto> response = deadlineController.setOrUpdateDeadline(1L, new DeadlineDto());
        assertEquals(ResponseEntity.status(503).build(), response);
    }
}
