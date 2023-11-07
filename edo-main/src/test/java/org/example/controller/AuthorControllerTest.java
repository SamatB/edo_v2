package org.example.controller;

import org.example.dto.AuthorDto;
import org.example.feign.AuthorFeignClient;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doReturn;

@DisplayName("Тест контроллера авторов")
public class AuthorControllerTest {
    @Mock
    AuthorFeignClient authorFeignClient;
    @InjectMocks
    AuthorController authorController;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void getAuthorsByFirstLetters() {
        List<AuthorDto> authors = new ArrayList<>();
        doReturn(authors).when(authorFeignClient).getAuthorsByFirstLetters(anyString());
        ResponseEntity<List<AuthorDto>> response = authorController.getAuthorsByFirstLetters("Ива");
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(authors, response.getBody());
    }
}
