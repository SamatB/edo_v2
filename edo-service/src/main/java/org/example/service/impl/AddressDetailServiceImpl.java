package org.example.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.entity.Address;
import org.example.repository.AddressDetailsRepository;
import org.example.service.AddressDetailService;
import org.springframework.stereotype.Service;

import java.util.Optional;


/**
 * Сервис для работы с сущностью Address.
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class AddressDetailServiceImpl implements AddressDetailService {
    private final AddressDetailsRepository addressDetailsRepository;

    /**
     * Сохраняет адрес в базе данных.
     * Если такой адрес уже есть в базе, возвращает его
     *
     * @param addressDetails объект Address
     * @return сохраненный объект Address
     */
    public Address saveAddress(Address addressDetails) {
        return Optional.ofNullable(addressDetails)
                .map(Address::getFullAddress)
                .flatMap(addressDetailsRepository::findByFullAddress)
                .map(entity -> {
                            addressDetailsRepository.save(entity);
                            log.info("В базу данных сохранен объект Address: " + entity.getFullAddress());
                            return entity;
                })
                .orElseThrow(()->new IllegalArgumentException("Ошибка сохранения адреса: адрес не должен быть null"));
    }
}
