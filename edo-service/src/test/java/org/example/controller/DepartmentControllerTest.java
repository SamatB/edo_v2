package org.example.controller;

import org.example.service.DeadlineService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

/**
 * Тесты для класса DeadlineController.
 */

@DisplayName("Тестирование контроллера для работы с департаментом")
public class DepartmentControllerTest {

    @Mock
    private DeadlineService deadlineService;

    @InjectMocks
    private DepartmentController departmentController;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    /**
     * Тест для метода getDepartmentByName.
     */

    @Test
    public void getDepartmentByName_should_positive(){

    }
}
