package org.example.utils;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;

import java.io.IOException;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Класс, десериализующий дату из JSON в формат ZonedDateTime
 */
public class ZonedDateTimeDeserializer extends JsonDeserializer<ZonedDateTime> {
    private static final DateTimeFormatter formatter = DateTimeFormatter.ISO_DATE_TIME;

    @Override
    public ZonedDateTime deserialize(JsonParser p, DeserializationContext ctxt) throws IOException {
        String dateString = p.getText();
        return ZonedDateTime.parse(dateString, formatter);
    }
}
