package org.example.service;

import org.example.entity.Address;
import org.example.repository.AddressDetailsRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * Тесты для класса AddressDetailService.
 */
class AddressDetailServiceTest {

    @Mock
    private AddressDetailsRepository addressDetailsRepository;

    @InjectMocks
    private AddressDetailService addressDetailService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    /**
     * Тест для метода saveAddress.
     */
    @Test
    void saveAddress() {
        Address addressMock = mock(Address.class);
        when(addressDetailsRepository.save(addressMock)).thenReturn(addressMock);

        Address result = addressDetailService.saveAddress(addressMock);
        assertEquals(addressMock, result);
    }
}