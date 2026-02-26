package pl.nowakowski.infrastructure.database.repository.mapper;

import org.junit.jupiter.api.Test;
import pl.nowakowski.domain.CarToService;
import pl.nowakowski.infrastructure.database.entity.CarToServiceEntity;

import static org.junit.jupiter.api.Assertions.*;

class CarToServiceEntityMapperTest {

    private final CarToServiceEntityMapper mapper = new CarToServiceEntityMapperImpl();

    @Test
    void shouldMapFromEntity() {
        // Given
        CarToServiceEntity entity = CarToServiceEntity.builder()
                .carToServiceId(1)
                .vin("1FT7X2B60FEA74019")
                .brand("Ford")
                .model("F-150")
                .year(2020)
                .build();

        // When
        CarToService result = mapper.mapFromEntity(entity);

        // Then
        assertNotNull(result);
        assertEquals(1, result.getCarToServiceId());
        assertEquals("1FT7X2B60FEA74019", result.getVin());
        assertEquals("Ford", result.getBrand());
        assertEquals("F-150", result.getModel());
        assertEquals(2020, result.getYear());
    }

    @Test
    void shouldMapToEntity() {
        // Given
        CarToService car = CarToService.builder()
                .carToServiceId(1)
                .vin("1FT7X2B60FEA74019")
                .brand("Ford")
                .model("F-150")
                .year(2020)
                .build();

        // When
        CarToServiceEntity result = mapper.mapToEntity(car);

        // Then
        assertNotNull(result);
        assertEquals("1FT7X2B60FEA74019", result.getVin());
        assertEquals("Ford", result.getBrand());
        assertEquals("F-150", result.getModel());
        assertEquals(2020, result.getYear());
    }

    @Test
    void shouldHandleNullEntity() {
        // When
        CarToService result = mapper.mapFromEntity(null);

        // Then
        assertNull(result);
    }

    @Test
    void shouldHandleNullDomain() {
        // When
        CarToServiceEntity result = mapper.mapToEntity(null);

        // Then
        assertNull(result);
    }
}
