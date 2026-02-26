/*
package pl.nowakowski.infrastructure.database.repository.mapper;

import org.junit.jupiter.api.Test;
import pl.nowakowski.domain.Invoice;
import pl.nowakowski.infrastructure.database.entity.InvoiceEntity;

import java.time.OffsetDateTime;
import java.time.ZoneId;

import static org.junit.jupiter.api.Assertions.*;

class InvoiceEntityMapperTest {

    private final InvoiceEntityMapper mapper = new InvoiceEntityMapperImpl();

    @Test
    void shouldMapFromEntity() {
        // Given
        OffsetDateTime now = OffsetDateTime.now(ZoneId.of("Europe/Warsaw"));
        InvoiceEntity entity = InvoiceEntity.builder()
                .invoiceId(1)
                .invoiceNumber("INV-001")
                .dateTime(now)
                .build();

        // When
        Invoice result = mapper.mapFromEntity(entity);

        // Then
        assertNotNull(result);
        assertEquals(1, result.getInvoiceId());
        assertEquals("INV-001", result.getInvoiceNumber());
        assertEquals(now, result.getDateTime());
    }

    @Test
    void shouldMapToEntity() {
        // Given
        OffsetDateTime now = OffsetDateTime.now(ZoneId.of("Europe/Warsaw"));
        Invoice invoice = Invoice.builder()
                .invoiceId(1)
                .invoiceNumber("INV-001")
                .dateTime(now)
                .build();

        // When
        InvoiceEntity result = mapper.mapToEntity(invoice);

        // Then
        assertNotNull(result);
        assertEquals("INV-001", result.getInvoiceNumber());
        assertEquals(now, result.getDateTime());
    }

    @Test
    void shouldHandleNullEntity() {
        // When
        Invoice result = mapper.mapFromEntity(null);

        // Then
        assertNull(result);
    }

    @Test
    void shouldHandleNullDomain() {
        // When
        InvoiceEntity result = mapper.mapToEntity(null);

        // Then
        assertNull(result);
    }
}
*/
