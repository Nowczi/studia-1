/*
package pl.nowakowski.infrastructure.database.repository.mapper;

import org.junit.jupiter.api.Test;
import pl.nowakowski.domain.Service;
import pl.nowakowski.infrastructure.database.entity.ServiceEntity;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

class ServiceEntityMapperTest {

    private final ServiceEntityMapper mapper = new ServiceEntityMapperImpl();

    @Test
    void shouldMapFromEntity() {
        // Given
        ServiceEntity entity = ServiceEntity.builder()
                .serviceId(1)
                .serviceCode("SRV-001")
                .description("Oil change")
                .price(new BigDecimal("100.00"))
                .build();

        // When
        Service result = mapper.mapFromEntity(entity);

        // Then
        assertNotNull(result);
        assertEquals(1, result.getServiceId());
        assertEquals("SRV-001", result.getServiceCode());
        assertEquals("Oil change", result.getDescription());
        assertEquals(new BigDecimal("100.00"), result.getPrice());
    }

    @Test
    void shouldMapToEntity() {
        // Given
        Service service = Service.builder()
                .serviceId(1)
                .serviceCode("SRV-001")
                .description("Oil change")
                .price(new BigDecimal("100.00"))
                .build();

        // When
        ServiceEntity result = mapper.mapToEntity(service);

        // Then
        assertNotNull(result);
        assertEquals("SRV-001", result.getServiceCode());
        assertEquals("Oil change", result.getDescription());
        assertEquals(new BigDecimal("100.00"), result.getPrice());
    }

    @Test
    void shouldHandleNullEntity() {
        // When
        Service result = mapper.mapFromEntity(null);

        // Then
        assertNull(result);
    }

    @Test
    void shouldHandleNullDomain() {
        // When
        ServiceEntity result = mapper.mapToEntity(null);

        // Then
        assertNull(result);
    }
}
*/
