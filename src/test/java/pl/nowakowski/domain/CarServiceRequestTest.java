package pl.nowakowski.domain;

import org.junit.jupiter.api.Test;

import java.time.OffsetDateTime;
import java.time.ZoneId;
import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class CarServiceRequestTest {

    @Test
    void shouldBuildCarServiceRequestSuccessfully() {
        // Given
        Customer customer = Customer.builder()
                .customerId(1)
                .name("John")
                .surname("Doe")
                .email("john@example.com")
                .build();

        CarToService car = CarToService.builder()
                .carToServiceId(1)
                .vin("1FT7X2B60FEA74019")
                .brand("Ford")
                .model("F-150")
                .year(2020)
                .build();

        OffsetDateTime receivedDateTime = OffsetDateTime.now(ZoneId.of("Europe/Warsaw"));

        // When
        CarServiceRequest request = CarServiceRequest.builder()
                .carServiceRequestId(1)
                .carServiceRequestNumber("2024.1.1-10.30.0.50")
                .receivedDateTime(receivedDateTime)
                .customerComment("Oil change needed")
                .customer(customer)
                .car(car)
                .build();

        // Then
        assertNotNull(request);
        assertEquals(1, request.getCarServiceRequestId());
        assertEquals("2024.1.1-10.30.0.50", request.getCarServiceRequestNumber());
        assertEquals(receivedDateTime, request.getReceivedDateTime());
        assertEquals("Oil change needed", request.getCustomerComment());
        assertEquals(customer, request.getCustomer());
        assertEquals(car, request.getCar());
    }

    @Test
    void shouldBuildCarServiceRequestWithServiceMechanicsAndParts() {
        // Given
        ServiceMechanic serviceMechanic = ServiceMechanic.builder()
                .serviceMechanicId(1)
                .hours(2)
                .comment("Work completed")
                .build();

        ServicePart servicePart = ServicePart.builder()
                .servicePartId(1)
                .quantity(2)
                .build();

        Set<ServiceMechanic> serviceMechanics = new HashSet<>();
        serviceMechanics.add(serviceMechanic);

        Set<ServicePart> serviceParts = new HashSet<>();
        serviceParts.add(servicePart);

        // When
        CarServiceRequest request = CarServiceRequest.builder()
                .carServiceRequestId(1)
                .carServiceRequestNumber("2024.1.1-10.30.0.50")
                .serviceMechanics(serviceMechanics)
                .serviceParts(serviceParts)
                .build();

        // Then
        assertNotNull(request.getServiceMechanics());
        assertEquals(1, request.getServiceMechanics().size());
        assertNotNull(request.getServiceParts());
        assertEquals(1, request.getServiceParts().size());
    }

    @Test
    void shouldTestWithMethod() {
        // Given
        CarServiceRequest originalRequest = CarServiceRequest.builder()
                .carServiceRequestId(1)
                .carServiceRequestNumber("2024.1.1-10.30.0.50")
                .customerComment("Oil change needed")
                .build();

        // When
        CarServiceRequest modifiedRequest = originalRequest.withCustomerComment("Brake inspection needed");

        // Then
        assertEquals("Brake inspection needed", modifiedRequest.getCustomerComment());
        assertEquals(originalRequest.getCarServiceRequestNumber(), modifiedRequest.getCarServiceRequestNumber());
    }

    @Test
    void shouldTestEqualsAndHashCodeBasedOnCarServiceRequestNumber() {
        // Given
        CarServiceRequest request1 = CarServiceRequest.builder()
                .carServiceRequestId(1)
                .carServiceRequestNumber("2024.1.1-10.30.0.50")
                .customerComment("Oil change needed")
                .build();

        CarServiceRequest request2 = CarServiceRequest.builder()
                .carServiceRequestId(2)
                .carServiceRequestNumber("2024.1.1-10.30.0.50")
                .customerComment("Brake inspection needed")
                .build();

        CarServiceRequest request3 = CarServiceRequest.builder()
                .carServiceRequestId(3)
                .carServiceRequestNumber("2024.1.2-11.00.0.60")
                .customerComment("Tire rotation needed")
                .build();

        // Then
        assertEquals(request1, request2);
        assertEquals(request1.hashCode(), request2.hashCode());
        assertNotEquals(request1, request3);
    }

    @Test
    void shouldTestToString() {
        // Given
        CarServiceRequest request = CarServiceRequest.builder()
                .carServiceRequestId(1)
                .carServiceRequestNumber("2024.1.1-10.30.0.50")
                .customerComment("Oil change needed")
                .build();

        // When
        String toString = request.toString();

        // Then
        assertTrue(toString.contains("2024.1.1-10.30.0.50"));
        assertTrue(toString.contains("Oil change needed"));
    }
}
