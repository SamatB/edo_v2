package org.example.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.entity.Address;
import org.example.repository.AddressDetailsRepository;
import org.example.service.AddressService;
import org.springframework.stereotype.Service;

import java.util.Optional;


/**
 * Сервис для работы с сущностью Address.
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class AddressServiceImpl implements AddressService {
    private final AddressDetailsRepository addressDetailsRepository;

    /**
     * Сохраняет адрес в базе данных.
     * Если такой адрес уже есть в базе, возвращает его
     *
     * @param addressDetails объект Address
     * @return сохраненный объект Address
     */
    public Address saveAddress(Address addressDetails) {
        if (addressDetails == null) {
            throw new IllegalArgumentException("Ошибка сохранения адреса: адрес не должен быть null");
        }
        log.info("Сохранение адреса в базу данных");
        return Optional.of(addressDetails)
                .map(Address::getFullAddress)
                .flatMap(addressDetailsRepository::findByFullAddress)
                .orElseGet(() -> {
                    Address newAddress = addressDetailsRepository.save(addressDetails);
                    log.info("В базу данных сохранен объект Address: " + newAddress.getFullAddress());
                    return newAddress;
                });
    }
}
