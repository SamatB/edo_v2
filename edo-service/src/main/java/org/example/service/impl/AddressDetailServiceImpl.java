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
        Optional<Address> address = addressDetailsRepository.findByFullAddress(addressDetails.getFullAddress());
        // Если такого адреса нет, создаем его
        if (address.isEmpty()) {
            addressDetails = addressDetailsRepository.save(addressDetails);
            log.info("В базу данных сохранен объект Address: " + addressDetails.getFullAddress());
        } else {
            // Если такой адрес уже есть в базе, возвращаем его
            addressDetails = address.get();
        }
        return addressDetails;
    }
}
