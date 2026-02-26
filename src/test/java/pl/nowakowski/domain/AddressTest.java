package pl.nowakowski.domain;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class AddressTest {

    @Test
    void shouldBuildAddressSuccessfully() {
        // Given
        Customer customer = Customer.builder()
                .customerId(1)
                .name("John")
                .surname("Doe")
                .email("john@example.com")
                .build();

        // When
        Address address = Address.builder()
                .addressId(1)
                .country("Poland")
                .city("Warsaw")
                .postalCode("00-001")
                .address("Main Street 123")
                .customer(customer)
                .build();

        // Then
        assertNotNull(address);
        assertEquals(1, address.getAddressId());
        assertEquals("Poland", address.getCountry());
        assertEquals("Warsaw", address.getCity());
        assertEquals("00-001", address.getPostalCode());
        assertEquals("Main Street 123", address.getAddress());
        assertEquals(customer, address.getCustomer());
    }

    @Test
    void shouldCreateAddressWithMinimalData() {
        // When
        Address address = Address.builder()
                .country("Poland")
                .city("Warsaw")
                .build();

        // Then
        assertNotNull(address);
        assertEquals("Poland", address.getCountry());
        assertEquals("Warsaw", address.getCity());
        assertNull(address.getAddressId());
        assertNull(address.getPostalCode());
        assertNull(address.getAddress());
        assertNull(address.getCustomer());
    }

    @Test
    void shouldTestWithMethod() {
        // Given
        Address originalAddress = Address.builder()
                .addressId(1)
                .country("Poland")
                .city("Warsaw")
                .postalCode("00-001")
                .address("Main Street 123")
                .build();

        // When
        Address modifiedAddress = originalAddress.withCity("Krakow");

        // Then
        assertEquals("Krakow", modifiedAddress.getCity());
        assertEquals(originalAddress.getCountry(), modifiedAddress.getCountry());
        assertEquals(originalAddress.getPostalCode(), modifiedAddress.getPostalCode());
    }

    @Test
    void shouldTestEqualsAndHashCode() {
        // Given
        Address address1 = Address.builder()
                .addressId(1)
                .country("Poland")
                .city("Warsaw")
                .build();

        Address address2 = Address.builder()
                .addressId(1)
                .country("Germany")
                .city("Berlin")
                .build();

        Address address3 = Address.builder()
                .addressId(2)
                .country("Poland")
                .city("Warsaw")
                .build();

        // Then
        assertEquals(address1, address2);
        assertEquals(address1.hashCode(), address2.hashCode());
        assertNotEquals(address1, address3);
    }

    @Test
    void shouldTestToString() {
        // Given
        Address address = Address.builder()
                .addressId(1)
                .country("Poland")
                .city("Warsaw")
                .postalCode("00-001")
                .address("Main Street 123")
                .build();

        // When
        String toString = address.toString();

        // Then
        assertTrue(toString.contains("Poland"));
        assertTrue(toString.contains("Warsaw"));
        assertTrue(toString.contains("00-001"));
        assertTrue(toString.contains("Main Street 123"));
    }
}
