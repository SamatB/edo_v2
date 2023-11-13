package org.example.controller;

import org.example.dto.FilePoolDto;
import org.example.service.impl.FilePoolServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

/**
 * Класс для юнит-тестирования контроллера FilePoolController
 */
@DisplayName("Тестирование контроллера FilePoolController")
class FilePoolControllerTest {
    @Mock
    private FilePoolServiceImpl filePoolService;
    @InjectMocks
    private FilePoolController filePoolController;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    /**
     * Проверка на возврат bad_request response, когда передается null
     */
    @Test
    @DisplayName("Should return a bad_request response when the FilePoolDto is null")
    void saveFilePoolWhenFilePoolDtoIsNullThenReturnBadRequest() {
        FilePoolDto filePoolDto = null;

        ResponseEntity<String> response = filePoolController.saveFilePool(filePoolDto);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        verify(filePoolService, never()).saveFilePool(any(FilePoolDto.class));
    }

    /**
     * Проверка на корректное сохранение объекта FilePool с возвращением его UUID
     */
    @Test
    @DisplayName("Should save the FilePool and return the storage file UUID")
    void saveFilePoolAndReturnStorageFileId() {
        FilePoolDto filePoolDto = FilePoolDto.builder()
                .id(1L)
                .storageFileId(UUID.randomUUID())
                .name("test.txt")
                .extension("txt")
                .size(1024L)
                .pageCount(10)
                .uploadDate(ZonedDateTime.now())
                .archivedDate(null)
                .build();

        when(filePoolService.saveFilePool(filePoolDto)).thenReturn(filePoolDto);

        ResponseEntity<String> response = filePoolController.saveFilePool(filePoolDto);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(filePoolDto.getStorageFileId().toString(), response.getBody());

        verify(filePoolService, times(1)).saveFilePool(filePoolDto);
    }

    /**
     * Проверка на корректное получение списка UUID
     */
    @Test
    @DisplayName("Should return the List of UUID")
    void testGetListOfOldRequestFile() {
        List<UUID> uuidList = new ArrayList<>();
        uuidList.add(UUID.randomUUID());

        when(filePoolService.getUUIDByCreationDateBeforeFiveYears()).thenReturn(uuidList);

        ResponseEntity<List<UUID>> responseEntity = filePoolController.getListOfOldRequestFile();

        assertEquals(ResponseEntity.ok(uuidList), responseEntity);
        verify(filePoolService, times(1)).getUUIDByCreationDateBeforeFiveYears();
    }

    /**
     * Проверка на получение пустого списка UUID
     */
    @Test
    @DisplayName("Should return a bad_request response when the List of UUID is null")
    void testGetListOfOldRequestFileIsNullThenReturnBadRequest() {
        when(filePoolService.getUUIDByCreationDateBeforeFiveYears()).thenReturn(null);

        ResponseEntity<List<UUID>> responseEntity = filePoolController.getListOfOldRequestFile();

        assertEquals(ResponseEntity.badRequest().build(), responseEntity);
        verify(filePoolService, times(1)).getUUIDByCreationDateBeforeFiveYears();
    }

    @Test
    void markFilePoolRemoved_WithValidUuidList_ReturnsOkResponse() {
        List<UUID> uuidList = Arrays.asList(UUID.randomUUID(), UUID.randomUUID());
        ResponseEntity<?> expectedResponse = ResponseEntity.ok().build();

        ResponseEntity<?> actualResponse = filePoolController.markFilePoolRemoved(uuidList);

        assertEquals(expectedResponse, actualResponse);
        verify(filePoolService, times(1)).markThatTheFileHasBeenDeletedFromStorage(uuidList);
    }

    @Test
    void markFilePoolRemoved_WithNullUuidList_ReturnsBadRequestResponse() {
        ResponseEntity<?> expectedResponse = ResponseEntity.badRequest().build();

        ResponseEntity<?> actualResponse = filePoolController.markFilePoolRemoved(null);

        assertEquals(expectedResponse, actualResponse);
        verifyNoInteractions(filePoolService);
    }

}