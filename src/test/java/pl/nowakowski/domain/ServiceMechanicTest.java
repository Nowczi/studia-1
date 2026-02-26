package pl.nowakowski.domain;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ServiceMechanicTest {

    @Test
    void shouldBuildServiceMechanicSuccessfully() {
        // Given
        Mechanic mechanic = Mechanic.builder()
                .mechanicId(1)
                .name("John")
                .surname("Doe")
                .pesel("12345678901")
                .build();

        Service service = Service.builder()
                .serviceId(1)
                .serviceCode("SRV-001")
                .description("Oil change")
                .build();

        CarServiceRequest carServiceRequest = CarServiceRequest.builder()
                .carServiceRequestId(1)
                .carServiceRequestNumber("2024.1.1-10.30.0.50")
                .build();

        // When
        ServiceMechanic serviceMechanic = ServiceMechanic.builder()
                .serviceMechanicId(1)
                .hours(2)
                .comment("Work completed")
                .quantity(1)
                .mechanic(mechanic)
                .service(service)
                .carServiceRequest(carServiceRequest)
                .build();

        // Then
        assertNotNull(serviceMechanic);
        assertEquals(1, serviceMechanic.getServiceMechanicId());
        assertEquals(2, serviceMechanic.getHours());
        assertEquals("Work completed", serviceMechanic.getComment());
        assertEquals(1, serviceMechanic.getQuantity());
        assertEquals(mechanic, serviceMechanic.getMechanic());
        assertEquals(service, serviceMechanic.getService());
        assertEquals(carServiceRequest, serviceMechanic.getCarServiceRequest());
    }

    @Test
    void shouldTestWithMethod() {
        // Given
        ServiceMechanic originalServiceMechanic = ServiceMechanic.builder()
                .serviceMechanicId(1)
                .hours(2)
                .comment("Work completed")
                .build();

        // When
        ServiceMechanic modifiedServiceMechanic = originalServiceMechanic.withHours(4);

        // Then
        assertEquals(4, modifiedServiceMechanic.getHours());
        assertEquals(originalServiceMechanic.getComment(), modifiedServiceMechanic.getComment());
    }

    @Test
    void shouldTestEqualsAndHashCodeBasedOnServiceMechanicId() {
        // Given
        ServiceMechanic serviceMechanic1 = ServiceMechanic.builder()
                .serviceMechanicId(1)
                .hours(2)
                .comment("Work completed")
                .build();

        ServiceMechanic serviceMechanic2 = ServiceMechanic.builder()
                .serviceMechanicId(1)
                .hours(3)
                .comment("Different comment")
                .build();

        ServiceMechanic serviceMechanic3 = ServiceMechanic.builder()
                .serviceMechanicId(2)
                .hours(2)
                .comment("Work completed")
                .build();

        // Then
        assertEquals(serviceMechanic1, serviceMechanic2);
        assertEquals(serviceMechanic1.hashCode(), serviceMechanic2.hashCode());
        assertNotEquals(serviceMechanic1, serviceMechanic3);
    }

    @Test
    void shouldTestToString() {
        // Given
        ServiceMechanic serviceMechanic = ServiceMechanic.builder()
                .serviceMechanicId(1)
                .hours(2)
                .comment("Work completed")
                .quantity(1)
                .build();

        // When
        String toString = serviceMechanic.toString();

        // Then
        assertTrue(toString.contains("1"));
        assertTrue(toString.contains("2"));
        assertTrue(toString.contains("Work completed"));
    }
}
