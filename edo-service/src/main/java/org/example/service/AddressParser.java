package org.example.service;

import org.example.entity.Address;
import org.springframework.stereotype.Service;

/**
 *  Интерефейс описывающий метод parse для парсинга адреса через геокодер от яндекса
 */

@Service
public interface AddressParser {
    Address parse(String addressString);
}
