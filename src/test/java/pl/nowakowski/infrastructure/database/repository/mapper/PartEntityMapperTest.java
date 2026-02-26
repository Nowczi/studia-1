/*
package pl.nowakowski.infrastructure.database.repository.mapper;

import org.junit.jupiter.api.Test;
import pl.nowakowski.domain.Part;
import pl.nowakowski.infrastructure.database.entity.PartEntity;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

class PartEntityMapperTest {

    private final PartEntityMapper mapper = new PartEntityMapperImpl();

    @Test
    void shouldMapFromEntity() {
        // Given
        PartEntity entity = PartEntity.builder()
                .partId(1)
                .serialNumber("PART-001")
                .description("Brake pads")
                .price(new BigDecimal("150.00"))
                .build();

        // When
        Part result = mapper.mapFromEntity(entity);

        // Then
        assertNotNull(result);
        assertEquals(1, result.getPartId());
        assertEquals("PART-001", result.getSerialNumber());
        assertEquals("Brake pads", result.getDescription());
        assertEquals(new BigDecimal("150.00"), result.getPrice());
    }

    @Test
    void shouldMapToEntity() {
        // Given
        Part part = Part.builder()
                .partId(1)
                .serialNumber("PART-001")
                .description("Brake pads")
                .price(new BigDecimal("150.00"))
                .build();

        // When
        PartEntity result = mapper.mapToEntity(part);

        // Then
        assertNotNull(result);
        assertEquals("PART-001", result.getSerialNumber());
        assertEquals("Brake pads", result.getDescription());
        assertEquals(new BigDecimal("150.00"), result.getPrice());
    }

    @Test
    void shouldHandleNullEntity() {
        // When
        Part result = mapper.mapFromEntity(null);

        // Then
        assertNull(result);
    }

    @Test
    void shouldHandleNullDomain() {
        // When
        PartEntity result = mapper.mapToEntity(null);

        // Then
        assertNull(result);
    }
}
*/
