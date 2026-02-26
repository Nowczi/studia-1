package pl.nowakowski.domain;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ServicePartTest {

    @Test
    void shouldBuildServicePartSuccessfully() {
        // Given
        Part part = Part.builder()
                .partId(1)
                .serialNumber("PART-001")
                .description("Brake pads")
                .build();

        CarServiceRequest carServiceRequest = CarServiceRequest.builder()
                .carServiceRequestId(1)
                .carServiceRequestNumber("2024.1.1-10.30.0.50")
                .build();

        // When
        ServicePart servicePart = ServicePart.builder()
                .servicePartId(1)
                .quantity(2)
                .part(part)
                .carServiceRequest(carServiceRequest)
                .build();

        // Then
        assertNotNull(servicePart);
        assertEquals(1, servicePart.getServicePartId());
        assertEquals(2, servicePart.getQuantity());
        assertEquals(part, servicePart.getPart());
        assertEquals(carServiceRequest, servicePart.getCarServiceRequest());
    }

    @Test
    void shouldTestWithMethod() {
        // Given
        ServicePart originalServicePart = ServicePart.builder()
                .servicePartId(1)
                .quantity(2)
                .build();

        // When
        ServicePart modifiedServicePart = originalServicePart.withQuantity(4);

        // Then
        assertEquals(4, modifiedServicePart.getQuantity());
        assertEquals(originalServicePart.getServicePartId(), modifiedServicePart.getServicePartId());
    }

    @Test
    void shouldTestEqualsAndHashCodeBasedOnServicePartId() {
        // Given
        ServicePart servicePart1 = ServicePart.builder()
                .servicePartId(1)
                .quantity(2)
                .build();

        ServicePart servicePart2 = ServicePart.builder()
                .servicePartId(1)
                .quantity(3)
                .build();

        ServicePart servicePart3 = ServicePart.builder()
                .servicePartId(2)
                .quantity(2)
                .build();

        // Then
        assertEquals(servicePart1, servicePart2);
        assertEquals(servicePart1.hashCode(), servicePart2.hashCode());
        assertNotEquals(servicePart1, servicePart3);
    }

    @Test
    void shouldTestToString() {
        // Given
        ServicePart servicePart = ServicePart.builder()
                .servicePartId(1)
                .quantity(2)
                .build();

        // When
        String toString = servicePart.toString();

        // Then
        assertTrue(toString.contains("1"));
        assertTrue(toString.contains("2"));
    }
}
