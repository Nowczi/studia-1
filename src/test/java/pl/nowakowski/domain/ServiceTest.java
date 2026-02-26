package pl.nowakowski.domain;

import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class ServiceTest {

    @Test
    void shouldBuildServiceSuccessfully() {
        // Given & When
        Service service = Service.builder()
                .serviceId(1)
                .serviceCode("SRV-001")
                .description("Oil change")
                .price(new BigDecimal("100.00"))
                .build();

        // Then
        assertNotNull(service);
        assertEquals(1, service.getServiceId());
        assertEquals("SRV-001", service.getServiceCode());
        assertEquals("Oil change", service.getDescription());
        assertEquals(new BigDecimal("100.00"), service.getPrice());
    }

    @Test
    void shouldBuildServiceWithServiceMechanics() {
        // Given
        ServiceMechanic serviceMechanic = ServiceMechanic.builder()
                .serviceMechanicId(1)
                .hours(2)
                .comment("Completed")
                .build();

        Set<ServiceMechanic> serviceMechanics = new HashSet<>();
        serviceMechanics.add(serviceMechanic);

        // When
        Service service = Service.builder()
                .serviceId(1)
                .serviceCode("SRV-001")
                .description("Oil change")
                .price(new BigDecimal("100.00"))
                .serviceMechanics(serviceMechanics)
                .build();

        // Then
        assertNotNull(service.getServiceMechanics());
        assertEquals(1, service.getServiceMechanics().size());
    }

    @Test
    void shouldTestWithMethod() {
        // Given
        Service originalService = Service.builder()
                .serviceId(1)
                .serviceCode("SRV-001")
                .description("Oil change")
                .price(new BigDecimal("100.00"))
                .build();

        // When
        Service modifiedService = originalService.withPrice(new BigDecimal("120.00"));

        // Then
        assertEquals(new BigDecimal("120.00"), modifiedService.getPrice());
        assertEquals(originalService.getServiceCode(), modifiedService.getServiceCode());
    }

    @Test
    void shouldTestEqualsAndHashCodeBasedOnServiceCode() {
        // Given
        Service service1 = Service.builder()
                .serviceId(1)
                .serviceCode("SRV-001")
                .description("Oil change")
                .build();

        Service service2 = Service.builder()
                .serviceId(2)
                .serviceCode("SRV-001")
                .description("Brake inspection")
                .build();

        Service service3 = Service.builder()
                .serviceId(3)
                .serviceCode("SRV-002")
                .description("Tire rotation")
                .build();

        // Then
        assertEquals(service1, service2);
        assertEquals(service1.hashCode(), service2.hashCode());
        assertNotEquals(service1, service3);
    }

    @Test
    void shouldTestToString() {
        // Given
        Service service = Service.builder()
                .serviceId(1)
                .serviceCode("SRV-001")
                .description("Oil change")
                .price(new BigDecimal("100.00"))
                .build();

        // When
        String toString = service.toString();

        // Then
        assertTrue(toString.contains("SRV-001"));
        assertTrue(toString.contains("Oil change"));
        assertTrue(toString.contains("100.00"));
    }
}
