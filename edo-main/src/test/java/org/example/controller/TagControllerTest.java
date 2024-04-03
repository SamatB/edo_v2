package org.example.controller;

import org.example.dto.TagDto;
import org.example.feign.TagFeignClient;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
public class TagControllerTest {
    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }
    @Mock
    TagFeignClient tagFeignClient;
    @InjectMocks
    TagController tagController;


    /**
     * Тест для метода saveTag.
     */
    @Test
    public void testSaveAppeal() {
        TagDto tagDto = new TagDto();
        when(tagFeignClient.saveTag(any(TagDto.class))).thenReturn(tagDto);

        ResponseEntity<TagDto> response = tagController.saveTag(tagDto);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(tagDto, response.getBody());
    }

}
