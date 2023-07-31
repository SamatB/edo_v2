package org.example.service.impl;

import lombok.SneakyThrows;
import org.apache.commons.collections4.CollectionUtils;
import org.example.entity.Address;
import org.example.service.AddressParser;
import org.example.utils.GeocodeResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Iterator;
import java.util.Optional;

/**
 *  Класс реализующий парсинг адресса через RestTemplate запрос до геокодера от яндекса
 *  и с единственным методом parse.
 *  Дополнительный утилитный класс GeocodeResponse находится в utils
 */
@Service
public class AddressParserImpl implements AddressParser {
    @Value("${yandex.geo.url}")
    private String url;

    private static final String STREET = "street";
    private static final String HOUSE = "house";
    private static final String CITY = "locality";
    private static final String REGION = "province";
    private static final String COUNTRY = "country";

    /**
     * Метод для парсинга адреса через геокодер
     * index, building, housing выходят отсюда всегда null
     * т.к. в геокодере нет таких данных
     */
    @Override
    @SneakyThrows
    public Address parse(String addressString) {
        RestTemplate restTemplate = new RestTemplate();
        GeocodeResponse geocodeResponse = restTemplate.getForObject(url + addressString, GeocodeResponse.class);

        return Optional.ofNullable(geocodeResponse)
                .map(GeocodeResponse::getResponse)
                .map(GeocodeResponse.Response::getGeoObjectCollection)
                .filter(collection -> CollectionUtils.isNotEmpty(collection.getFeatureMember()))
                .map(collection -> collection.getFeatureMember().iterator())
                .filter(Iterator::hasNext)
                .map(Iterator::next)
                .map(GeocodeResponse.FeatureMember::getGeoObject)
                .map(GeocodeResponse.GeoObject::getMetaDataProperty)
                .map(GeocodeResponse.MetaDataProperty::getGeocoderMetaData)
                .map(GeocodeResponse.GeocoderMetaData::getAddress)
                .map(address -> {
                    Address result = new Address();
                    result.setFullAddress(address.getFormattedAddress());
                    for (GeocodeResponse.Components component : address.getComponents()) {
                        switch (component.getKind()) {
                            case STREET -> result.setStreet(component.getName());
                            case HOUSE -> result.setHouse(component.getName());
                            case CITY -> result.setCity(component.getName());
                            case REGION -> result.setRegion(component.getName());
                            case COUNTRY -> result.setCountry(component.getName());
                            default -> {
                            }
                        }
                    }
                    return result;
                })
                .orElse(null);
    }
}