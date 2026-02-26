package pl.nowakowski.domain;

import org.junit.jupiter.api.Test;

import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class SalesmanTest {

    @Test
    void shouldBuildSalesmanSuccessfully() {
        // Given & When
        Salesman salesman = Salesman.builder()
                .salesmanId(1)
                .name("John")
                .surname("Doe")
                .pesel("12345678901")
                .userId(100)
                .userName("johndoe")
                .build();

        // Then
        assertNotNull(salesman);
        assertEquals(1, salesman.getSalesmanId());
        assertEquals("John", salesman.getName());
        assertEquals("Doe", salesman.getSurname());
        assertEquals("12345678901", salesman.getPesel());
        assertEquals(100, salesman.getUserId());
        assertEquals("johndoe", salesman.getUserName());
    }

    @Test
    void shouldBuildSalesmanWithInvoices() {
        // Given
        Invoice invoice = Invoice.builder()
                .invoiceId(1)
                .invoiceNumber("INV-001")
                .build();

        Set<Invoice> invoices = new HashSet<>();
        invoices.add(invoice);

        // When
        Salesman salesman = Salesman.builder()
                .salesmanId(1)
                .name("John")
                .surname("Doe")
                .pesel("12345678901")
                .invoices(invoices)
                .build();

        // Then
        assertNotNull(salesman.getInvoices());
        assertEquals(1, salesman.getInvoices().size());
    }

    @Test
    void shouldTestWithMethod() {
        // Given
        Salesman originalSalesman = Salesman.builder()
                .salesmanId(1)
                .name("John")
                .surname("Doe")
                .pesel("12345678901")
                .build();

        // When
        Salesman modifiedSalesman = originalSalesman.withName("Jane");

        // Then
        assertEquals("Jane", modifiedSalesman.getName());
        assertEquals(originalSalesman.getSurname(), modifiedSalesman.getSurname());
    }

    @Test
    void shouldTestEqualsAndHashCodeBasedOnPesel() {
        // Given
        Salesman salesman1 = Salesman.builder()
                .salesmanId(1)
                .name("John")
                .surname("Doe")
                .pesel("12345678901")
                .build();

        Salesman salesman2 = Salesman.builder()
                .salesmanId(2)
                .name("Jane")
                .surname("Smith")
                .pesel("12345678901")
                .build();

        Salesman salesman3 = Salesman.builder()
                .salesmanId(3)
                .name("Bob")
                .surname("Wilson")
                .pesel("98765432109")
                .build();

        // Then
        assertEquals(salesman1, salesman2);
        assertEquals(salesman1.hashCode(), salesman2.hashCode());
        assertNotEquals(salesman1, salesman3);
    }

    @Test
    void shouldTestToString() {
        // Given
        Salesman salesman = Salesman.builder()
                .salesmanId(1)
                .name("John")
                .surname("Doe")
                .pesel("12345678901")
                .build();

        // When
        String toString = salesman.toString();

        // Then
        assertTrue(toString.contains("John"));
        assertTrue(toString.contains("Doe"));
        assertTrue(toString.contains("12345678901"));
    }
}
