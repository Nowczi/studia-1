package pl.nowakowski.domain;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CarPurchaseRequestTest {

    @Test
    void shouldBuildCarPurchaseRequestForNewCustomer() {
        // Given & When
        CarPurchaseRequest request = CarPurchaseRequest.builder()
                .customerName("John")
                .customerSurname("Doe")
                .customerPhone("+48 123 456 789")
                .customerEmail("john@example.com")
                .customerAddressCountry("Poland")
                .customerAddressCity("Warsaw")
                .customerAddressPostalCode("00-001")
                .customerAddressStreet("Main Street 123")
                .carVin("1FT7X2B60FEA74019")
                .salesmanPesel("12345678901")
                .build();

        // Then
        assertNotNull(request);
        assertNull(request.getExistingCustomerEmail());
        assertEquals("John", request.getCustomerName());
        assertEquals("Doe", request.getCustomerSurname());
        assertEquals("+48 123 456 789", request.getCustomerPhone());
        assertEquals("john@example.com", request.getCustomerEmail());
        assertEquals("Poland", request.getCustomerAddressCountry());
        assertEquals("Warsaw", request.getCustomerAddressCity());
        assertEquals("00-001", request.getCustomerAddressPostalCode());
        assertEquals("Main Street 123", request.getCustomerAddressStreet());
        assertEquals("1FT7X2B60FEA74019", request.getCarVin());
        assertEquals("12345678901", request.getSalesmanPesel());
    }

    @Test
    void shouldBuildCarPurchaseRequestForExistingCustomer() {
        // Given & When
        CarPurchaseRequest request = CarPurchaseRequest.builder()
                .existingCustomerEmail("existing@example.com")
                .carVin("1FT7X2B60FEA74019")
                .salesmanPesel("12345678901")
                .build();

        // Then
        assertNotNull(request);
        assertEquals("existing@example.com", request.getExistingCustomerEmail());
        assertNull(request.getCustomerName());
        assertNull(request.getCustomerSurname());
        assertEquals("1FT7X2B60FEA74019", request.getCarVin());
        assertEquals("12345678901", request.getSalesmanPesel());
    }

    @Test
    void shouldTestWithMethod() {
        // Given
        CarPurchaseRequest originalRequest = CarPurchaseRequest.builder()
                .customerName("John")
                .customerSurname("Doe")
                .carVin("1FT7X2B60FEA74019")
                .salesmanPesel("12345678901")
                .build();

        // When
        CarPurchaseRequest modifiedRequest = originalRequest.withCustomerName("Jane");

        // Then
        assertEquals("Jane", modifiedRequest.getCustomerName());
        assertEquals(originalRequest.getCustomerSurname(), modifiedRequest.getCustomerSurname());
    }
}
