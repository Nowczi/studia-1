package pl.nowakowski.domain;

import org.junit.jupiter.api.Test;

import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class CustomerTest {

    @Test
    void shouldBuildCustomerSuccessfully() {
        // Given & When
        Customer customer = Customer.builder()
                .customerId(1)
                .name("John")
                .surname("Doe")
                .phone("+48 123 456 789")
                .email("john@example.com")
                .build();

        // Then
        assertNotNull(customer);
        assertEquals(1, customer.getCustomerId());
        assertEquals("John", customer.getName());
        assertEquals("Doe", customer.getSurname());
        assertEquals("+48 123 456 789", customer.getPhone());
        assertEquals("john@example.com", customer.getEmail());
    }

    @Test
    void shouldBuildCustomerWithAddress() {
        // Given
        Address address = Address.builder()
                .country("Poland")
                .city("Warsaw")
                .postalCode("00-001")
                .address("Main Street 123")
                .build();

        // When
        Customer customer = Customer.builder()
                .customerId(1)
                .name("John")
                .surname("Doe")
                .email("john@example.com")
                .address(address)
                .build();

        // Then
        assertNotNull(customer.getAddress());
        assertEquals("Poland", customer.getAddress().getCountry());
        assertEquals("Warsaw", customer.getAddress().getCity());
    }

    @Test
    void shouldReturnEmptySetWhenInvoicesIsNull() {
        // Given
        Customer customer = Customer.builder()
                .customerId(1)
                .name("John")
                .surname("Doe")
                .email("john@example.com")
                .invoices(null)
                .build();

        // When
        Set<Invoice> invoices = customer.getInvoices();

        // Then
        assertNotNull(invoices);
        assertTrue(invoices.isEmpty());
    }

    @Test
    void shouldReturnInvoicesWhenNotNull() {
        // Given
        Invoice invoice = Invoice.builder()
                .invoiceId(1)
                .invoiceNumber("INV-001")
                .build();

        Set<Invoice> invoices = new HashSet<>();
        invoices.add(invoice);

        Customer customer = Customer.builder()
                .customerId(1)
                .name("John")
                .surname("Doe")
                .email("john@example.com")
                .invoices(invoices)
                .build();

        // When
        Set<Invoice> result = customer.getInvoices();

        // Then
        assertNotNull(result);
        assertEquals(1, result.size());
    }

    @Test
    void shouldReturnEmptySetWhenCarServiceRequestsIsNull() {
        // Given
        Customer customer = Customer.builder()
                .customerId(1)
                .name("John")
                .surname("Doe")
                .email("john@example.com")
                .carServiceRequests(null)
                .build();

        // When
        Set<CarServiceRequest> requests = customer.getCarServiceRequests();

        // Then
        assertNotNull(requests);
        assertTrue(requests.isEmpty());
    }

    @Test
    void shouldTestWithMethod() {
        // Given
        Customer originalCustomer = Customer.builder()
                .customerId(1)
                .name("John")
                .surname("Doe")
                .email("john@example.com")
                .build();

        // When
        Customer modifiedCustomer = originalCustomer.withName("Jane");

        // Then
        assertEquals("Jane", modifiedCustomer.getName());
        assertEquals(originalCustomer.getSurname(), modifiedCustomer.getSurname());
    }

    @Test
    void shouldTestEqualsAndHashCodeBasedOnEmail() {
        // Given
        Customer customer1 = Customer.builder()
                .customerId(1)
                .name("John")
                .surname("Doe")
                .email("john@example.com")
                .build();

        Customer customer2 = Customer.builder()
                .customerId(2)
                .name("Jane")
                .surname("Smith")
                .email("john@example.com")
                .build();

        Customer customer3 = Customer.builder()
                .customerId(3)
                .name("Bob")
                .surname("Wilson")
                .email("bob@example.com")
                .build();

        // Then
        assertEquals(customer1, customer2);
        assertEquals(customer1.hashCode(), customer2.hashCode());
        assertNotEquals(customer1, customer3);
    }

    @Test
    void shouldTestToString() {
        // Given
        Customer customer = Customer.builder()
                .customerId(1)
                .name("John")
                .surname("Doe")
                .email("john@example.com")
                .build();

        // When
        String toString = customer.toString();

        // Then
        assertTrue(toString.contains("John"));
        assertTrue(toString.contains("Doe"));
        assertTrue(toString.contains("john@example.com"));
    }
}
