package org.example.controller;

import org.example.dto.FacsimileDto;
import org.example.feign.FacsimileFeignClient;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.test.web.servlet.MockMvc;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

/**
 * Тесты для класса ResolutionController.
 */
@DisplayName("Тестирование контроллера для работы с факсимиле")
class FacsimileControllerTest {

    @Mock
    FacsimileFeignClient facsimileFeignClient;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void saveFacsimile() {
        FacsimileDto facsimileDto = new FacsimileDto();
        when(facsimileFeignClient.saveFacsimile(facsimileDto)).thenReturn(facsimileDto);

        FacsimileDto savedDto = facsimileFeignClient.saveFacsimile(facsimileDto);

        assertEquals(facsimileDto, savedDto);
        verify(facsimileFeignClient, times(1)).saveFacsimile(facsimileDto);
    }

}