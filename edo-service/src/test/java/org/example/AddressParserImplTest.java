package org.example;

import org.example.entity.Address;
import org.example.service.AddressParser;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class AddressParserImplTest {

    @Autowired
    private AddressParser addressParser;

    private final String addressString = "Россия, Республика Башкортостан, Ишимбай, ул. Бульварная, 24";

    @Test
    public void testParse() {
        Address address = addressParser.parse(addressString);

        Assertions.assertNotNull(address);
        Assertions.assertNotNull(address.getFullAddress());
        Assertions.assertNotNull(address.getStreet());
        Assertions.assertNotNull(address.getCity());
        Assertions.assertNotNull(address.getRegion());
        Assertions.assertNotNull(address.getHouse());
        Assertions.assertNotNull(address.getCountry());
        Assertions.assertNull(address.getIndex());
        Assertions.assertNull(address.getBuilding());
        Assertions.assertNull(address.getHousing());
        Assertions.assertNull(address.getFlat());

        Assertions.assertEquals("Россия, Республика Башкортостан, Ишимбай, Бульварная улица, 24", address.getFullAddress());
        Assertions.assertEquals("Бульварная улица", address.getStreet());
        Assertions.assertEquals("Ишимбай", address.getCity());
        Assertions.assertEquals("Республика Башкортостан", address.getRegion());
        Assertions.assertEquals("24", address.getHouse());
        Assertions.assertEquals("Россия", address.getCountry());
    }
}