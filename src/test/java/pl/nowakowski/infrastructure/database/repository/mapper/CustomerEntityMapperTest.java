/*
package pl.nowakowski.infrastructure.database.repository.mapper;

import org.junit.jupiter.api.Test;
import pl.nowakowski.domain.Address;
import pl.nowakowski.domain.Customer;
import pl.nowakowski.infrastructure.database.entity.AddressEntity;
import pl.nowakowski.infrastructure.database.entity.CustomerEntity;

import java.util.HashSet;

import static org.junit.jupiter.api.Assertions.*;

class CustomerEntityMapperTest {

    private final CustomerEntityMapper mapper = new CustomerEntityMapperImpl();

    @Test
    void shouldMapFromEntity() {
        // Given
        AddressEntity addressEntity = AddressEntity.builder()
                .addressId(1)
                .country("Poland")
                .city("Warsaw")
                .postalCode("00-001")
                .address("Main Street 123")
                .build();

        CustomerEntity entity = CustomerEntity.builder()
                .customerId(1)
                .name("John")
                .surname("Doe")
                .phone("+48 123 456 789")
                .email("john@example.com")
                .address(addressEntity)
                .invoices(new HashSet<>())
                .carServiceRequests(new HashSet<>())
                .build();

        // When
        Customer result = mapper.mapFromEntity(entity);

        // Then
        assertNotNull(result);
        assertEquals(1, result.getCustomerId());
        assertEquals("John", result.getName());
        assertEquals("Doe", result.getSurname());
        assertEquals("+48 123 456 789", result.getPhone());
        assertEquals("john@example.com", result.getEmail());
        assertNotNull(result.getAddress());
        assertEquals("Poland", result.getAddress().getCountry());
    }

    @Test
    void shouldMapToEntity() {
        // Given
        Address address = Address.builder()
                .addressId(1)
                .country("Poland")
                .city("Warsaw")
                .postalCode("00-001")
                .address("Main Street 123")
                .build();

        Customer customer = Customer.builder()
                .customerId(1)
                .name("John")
                .surname("Doe")
                .phone("+48 123 456 789")
                .email("john@example.com")
                .address(address)
                .invoices(new HashSet<>())
                .carServiceRequests(new HashSet<>())
                .build();

        // When
        CustomerEntity result = mapper.mapToEntity(customer);

        // Then
        assertNotNull(result);
        assertEquals("John", result.getName());
        assertEquals("Doe", result.getSurname());
        assertEquals("+48 123 456 789", result.getPhone());
        assertEquals("john@example.com", result.getEmail());
        assertNotNull(result.getAddress());
        assertEquals("Poland", result.getAddress().getCountry());
    }

    @Test
    void shouldMapNullInvoicesToEmptySet() {
        // Given
        CustomerEntity entity = CustomerEntity.builder()
                .customerId(1)
                .name("John")
                .surname("Doe")
                .email("john@example.com")
                .invoices(null)
                .carServiceRequests(null)
                .build();

        // When
        Customer result = mapper.mapFromEntity(entity);

        // Then
        assertNotNull(result);
        assertNotNull(result.getInvoices());
        assertTrue(result.getInvoices().isEmpty());
        assertNotNull(result.getCarServiceRequests());
        assertTrue(result.getCarServiceRequests().isEmpty());
    }

    @Test
    void shouldHandleNullEntity() {
        // When
        Customer result = mapper.mapFromEntity(null);

        // Then
        assertNull(result);
    }

    @Test
    void shouldHandleNullDomain() {
        // When
        CustomerEntity result = mapper.mapToEntity(null);

        // Then
        assertNull(result);
    }
}
*/
