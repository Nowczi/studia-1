package pl.nowakowski.domain;

import org.junit.jupiter.api.Test;

import java.time.OffsetDateTime;
import java.time.ZoneId;

import static org.junit.jupiter.api.Assertions.*;

class InvoiceTest {

    @Test
    void shouldBuildInvoiceSuccessfully() {
        // Given
        CarToBuy car = CarToBuy.builder()
                .vin("1FT7X2B60FEA74019")
                .brand("Ford")
                .model("F-150")
                .build();

        Customer customer = Customer.builder()
                .name("John")
                .surname("Doe")
                .email("john@example.com")
                .build();

        Salesman salesman = Salesman.builder()
                .name("Jane")
                .surname("Smith")
                .pesel("12345678901")
                .build();

        OffsetDateTime now = OffsetDateTime.now(ZoneId.of("Europe/Warsaw"));

        // When
        Invoice invoice = Invoice.builder()
                .invoiceId(1)
                .invoiceNumber("INV-001")
                .dateTime(now)
                .car(car)
                .customer(customer)
                .salesman(salesman)
                .build();

        // Then
        assertNotNull(invoice);
        assertEquals(1, invoice.getInvoiceId());
        assertEquals("INV-001", invoice.getInvoiceNumber());
        assertEquals(now, invoice.getDateTime());
        assertEquals(car, invoice.getCar());
        assertEquals(customer, invoice.getCustomer());
        assertEquals(salesman, invoice.getSalesman());
    }

    @Test
    void shouldTestWithMethod() {
        // Given
        Invoice originalInvoice = Invoice.builder()
                .invoiceId(1)
                .invoiceNumber("INV-001")
                .build();

        // When
        Invoice modifiedInvoice = originalInvoice.withInvoiceNumber("INV-002");

        // Then
        assertEquals("INV-002", modifiedInvoice.getInvoiceNumber());
        assertEquals(originalInvoice.getInvoiceId(), modifiedInvoice.getInvoiceId());
    }

    @Test
    void shouldTestEqualsAndHashCodeBasedOnInvoiceNumber() {
        // Given
        Invoice invoice1 = Invoice.builder()
                .invoiceId(1)
                .invoiceNumber("INV-001")
                .build();

        Invoice invoice2 = Invoice.builder()
                .invoiceId(2)
                .invoiceNumber("INV-001")
                .build();

        Invoice invoice3 = Invoice.builder()
                .invoiceId(3)
                .invoiceNumber("INV-002")
                .build();

        // Then
        assertEquals(invoice1, invoice2);
        assertEquals(invoice1.hashCode(), invoice2.hashCode());
        assertNotEquals(invoice1, invoice3);
    }

    @Test
    void shouldTestToString() {
        // Given
        OffsetDateTime now = OffsetDateTime.now(ZoneId.of("Europe/Warsaw"));
        Invoice invoice = Invoice.builder()
                .invoiceId(1)
                .invoiceNumber("INV-001")
                .dateTime(now)
                .build();

        // When
        String toString = invoice.toString();

        // Then
        assertTrue(toString.contains("INV-001"));
    }
}
