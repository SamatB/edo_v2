package org.example.service.impl;

import jakarta.persistence.EntityExistsException;
import jakarta.persistence.EntityNotFoundException;
import org.example.dto.AppealDto;
import org.example.dto.EmployeeDto;
import org.example.dto.NomenclatureDto;
import org.example.entity.Appeal;
import org.example.entity.Nomenclature;
import org.example.mapper.AppealMapper;
import org.example.repository.AppealRepository;
import org.example.enums.StatusType;
import org.example.service.NomenclatureService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

class AppealServiceImplTest {

    @Mock
    private AppealRepository appealRepository;

    @Mock
    private AppealMapper appealMapper;

    @Mock
    private NomenclatureService nomenclatureService;

    @InjectMocks
    private AppealServiceImpl appealService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    /**
     * Тест для метода registerAppeal - успешная попытка регистрации обращения.
     */
    @Test
    public void testRegisterAppeal() {
        Appeal appeal = mock(Appeal.class);
        appeal.setStatusType(StatusType.REGISTERED);
        when(appealRepository.findById(anyLong())).thenReturn(Optional.of(appeal));

        appealService.registerAppeal(1L);

        verify(appeal, times(2)).setStatusType(StatusType.REGISTERED);
    }

    /**
     * Тест для метода registerAppeal - попытка регистрации обращения с несуществующим id.
     */
    @Test
    public void testRegisterAppealNotFound() {
        assertThrows(EntityNotFoundException.class, () -> appealService.registerAppeal(Long.MAX_VALUE));
    }

    /**
     * Тест для метода registerAppeal - попытка регистрации зарегистрированного обращения.
     */
    @Test
    public void testRegisterAppealRepeatedRegistration() {
        Appeal appeal = new Appeal();
        appeal.setStatusType(StatusType.REGISTERED);
        when(appealRepository.findById(anyLong())).thenReturn(Optional.of(appeal));

        assertThrows(EntityExistsException.class, () -> appealService.registerAppeal(1L));
    }

    /**
     * Тест для метода reserveNumberForAppeal.
     */
    @Test
    public void testReserveNumberForAppeal_Ok() {
        AppealDto appealDto = new AppealDto();
        Appeal appealMock = mock(Appeal.class);
        AppealDto expected = new AppealDto();
        NomenclatureDto nomenclatureDto = new NomenclatureDto();
        Nomenclature nomenclatureMock = mock(Nomenclature.class);
        String reservedNumber = "АБВ-12345";

        appealDto.setNomenclature(nomenclatureDto);
        expected.setNomenclature(nomenclatureDto);
        expected.setReservedNumber(reservedNumber);

        when(appealMapper.dtoToEntity(appealDto)).thenReturn(appealMock);
        when(nomenclatureService.generateNumberForAppeal(nomenclatureMock)).thenReturn(reservedNumber);
        when(appealMock.getNomenclature()).thenReturn(nomenclatureMock);
        when(appealRepository.save(appealMock)).thenReturn(appealMock);
        when(appealMapper.entityToDto(appealMock)).thenReturn(appealDto);

        doAnswer(new Answer<Void>() {
            public Void answer(InvocationOnMock invocation) {
                Object[] args = invocation.getArguments();
//                System.out.println("called with arguments: " + Arrays.toString(args));
                appealDto.setReservedNumber(args[0].toString());
                return null;
            }
        }).when(appealMock).setReservedNumber(anyString());

        AppealDto result = appealService.reserveNumberForAppeal(appealDto);
        verify(appealMapper, times(1)).dtoToEntity(appealDto);
        verify(nomenclatureService, times(1)).generateNumberForAppeal(nomenclatureMock);
        verify(appealRepository, times(1)).save(appealMock);
        verify(appealMapper, times(1)).entityToDto(appealMock);
        assertDoesNotThrow(() -> {
            appealService.reserveNumberForAppeal(appealDto);
        });
        assertEquals(expected.getReservedNumber(), result.getReservedNumber());
    }

    /**
     * Тест для метода reserveNumberForAppeal с нулевым обращением.
     */
    @Test
    public void testReserveNumberForAppeal_NullAppeal_ThrowsIllegalArgumentException() {
        assertThrowsExactly(IllegalArgumentException.class, () -> appealService.reserveNumberForAppeal(null));
    }

    /**
     * Тест для метода reserveNumberForAppeal с нулевым nomenclature.
     */
    @Test
    public void testReserveNumberForAppeal_NullNomenclature_ThrowsIllegalArgumentException() {
        AppealDto appealDto = new AppealDto();
        Appeal appealMock = mock(Appeal.class);

        appealDto.setCreator(new EmployeeDto());
        appealDto.setAnnotation("Тестовое обращение");

        when(appealMapper.dtoToEntity(appealDto)).thenReturn(appealMock);
        when(appealRepository.save(appealMock)).thenReturn(appealMock);
        when(appealMock.getNomenclature()).thenReturn(null);
        when(appealMapper.entityToDto(appealMock)).thenReturn(appealDto);

        assertThrowsExactly(IllegalArgumentException.class, () -> appealService.reserveNumberForAppeal(appealDto));
    }

    /**
     * Тест для метода reserveNumberForAppeal с уже существующим номером.
     */
    @Test
    public void testReserveNumberForAppeal_ExistingNumber_ThrowsEntityExistsException() {
        AppealDto appealDto = new AppealDto();
        Appeal appealMock = mock(Appeal.class);
        Nomenclature nomenclatureMock = mock(Nomenclature.class);
        String number = "АБВ-12345";
        appealDto.setNumber(number);
        appealMock.setNumber(number);

        when(appealMapper.dtoToEntity(appealDto)).thenReturn(appealMock);
        when(nomenclatureService.generateNumberForAppeal(nomenclatureMock)).thenReturn(number);
        when(appealMock.getNomenclature()).thenReturn(nomenclatureMock);
        when(appealMock.getNumber()).thenReturn(number);
        when(appealRepository.save(appealMock)).thenReturn(appealMock);
        when(appealMapper.entityToDto(appealMock)).thenReturn(appealDto);

        assertThrowsExactly(EntityExistsException.class, () -> appealService.reserveNumberForAppeal(appealDto));
    }

    /**
     * Тест для метода reserveNumberForAppeal с уже зарезервированным номером.
     */
    @Test
    public void testReserveNumberForAppeal_ExistingReservedNumber_ThrowsEntityExistsException() {
        AppealDto appealDto = new AppealDto();
        Appeal appealMock = mock(Appeal.class);
        Nomenclature nomenclatureMock = mock(Nomenclature.class);
        String reservedNumber = "АБВ-12345";
        appealDto.setReservedNumber(reservedNumber);
        appealMock.setReservedNumber(reservedNumber);

        when(appealMapper.dtoToEntity(appealDto)).thenReturn(appealMock);
        when(nomenclatureService.generateNumberForAppeal(nomenclatureMock)).thenReturn(reservedNumber);
        when(appealMock.getNomenclature()).thenReturn(nomenclatureMock);
        when(appealMock.getReservedNumber()).thenReturn(reservedNumber);
        when(appealRepository.save(appealMock)).thenReturn(appealMock);
        when(appealMapper.entityToDto(appealMock)).thenReturn(appealDto);

        assertThrowsExactly(EntityExistsException.class, () -> appealService.reserveNumberForAppeal(appealDto));
    }
}
