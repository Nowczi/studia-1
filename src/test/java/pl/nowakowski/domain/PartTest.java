package pl.nowakowski.domain;

import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class PartTest {

    @Test
    void shouldBuildPartSuccessfully() {
        // Given & When
        Part part = Part.builder()
                .partId(1)
                .serialNumber("PART-001")
                .description("Brake pads")
                .price(new BigDecimal("150.00"))
                .build();

        // Then
        assertNotNull(part);
        assertEquals(1, part.getPartId());
        assertEquals("PART-001", part.getSerialNumber());
        assertEquals("Brake pads", part.getDescription());
        assertEquals(new BigDecimal("150.00"), part.getPrice());
    }

    @Test
    void shouldBuildPartWithServiceParts() {
        // Given
        ServicePart servicePart = ServicePart.builder()
                .servicePartId(1)
                .quantity(2)
                .build();

        Set<ServicePart> serviceParts = new HashSet<>();
        serviceParts.add(servicePart);

        // When
        Part part = Part.builder()
                .partId(1)
                .serialNumber("PART-001")
                .description("Brake pads")
                .price(new BigDecimal("150.00"))
                .serviceParts(serviceParts)
                .build();

        // Then
        assertNotNull(part.getServiceParts());
        assertEquals(1, part.getServiceParts().size());
    }

    @Test
    void shouldTestWithMethod() {
        // Given
        Part originalPart = Part.builder()
                .partId(1)
                .serialNumber("PART-001")
                .description("Brake pads")
                .price(new BigDecimal("150.00"))
                .build();

        // When
        Part modifiedPart = originalPart.withPrice(new BigDecimal("175.00"));

        // Then
        assertEquals(new BigDecimal("175.00"), modifiedPart.getPrice());
        assertEquals(originalPart.getSerialNumber(), modifiedPart.getSerialNumber());
    }

    @Test
    void shouldTestEqualsAndHashCodeBasedOnSerialNumber() {
        // Given
        Part part1 = Part.builder()
                .partId(1)
                .serialNumber("PART-001")
                .description("Brake pads")
                .build();

        Part part2 = Part.builder()
                .partId(2)
                .serialNumber("PART-001")
                .description("Oil filter")
                .build();

        Part part3 = Part.builder()
                .partId(3)
                .serialNumber("PART-002")
                .description("Air filter")
                .build();

        // Then
        assertEquals(part1, part2);
        assertEquals(part1.hashCode(), part2.hashCode());
        assertNotEquals(part1, part3);
    }

    @Test
    void shouldTestToString() {
        // Given
        Part part = Part.builder()
                .partId(1)
                .serialNumber("PART-001")
                .description("Brake pads")
                .price(new BigDecimal("150.00"))
                .build();

        // When
        String toString = part.toString();

        // Then
        assertTrue(toString.contains("PART-001"));
        assertTrue(toString.contains("Brake pads"));
        assertTrue(toString.contains("150.00"));
    }

    @Test
    void shouldHaveNoneConstant() {
        // Then
        assertEquals("NONE", Part.NONE);
    }
}
