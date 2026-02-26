package pl.nowakowski.infrastructure.database.repository.mapper;

import org.junit.jupiter.api.Test;
import pl.nowakowski.domain.CarToBuy;
import pl.nowakowski.infrastructure.database.entity.CarToBuyEntity;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

class CarToBuyEntityMapperTest {

    private final CarToBuyEntityMapper mapper = new CarToBuyEntityMapperImpl();

    @Test
    void shouldMapFromEntity() {
        // Given
        CarToBuyEntity entity = CarToBuyEntity.builder()
                .carToBuyId(1)
                .vin("1FT7X2B60FEA74019")
                .brand("Ford")
                .model("F-150")
                .year(2020)
                .color("Red")
                .price(new BigDecimal("50000.00"))
                .build();

        // When
        CarToBuy result = mapper.mapFromEntity(entity);

        // Then
        assertNotNull(result);
        assertEquals(1, result.getCarToBuyId());
        assertEquals("1FT7X2B60FEA74019", result.getVin());
        assertEquals("Ford", result.getBrand());
        assertEquals("F-150", result.getModel());
        assertEquals(2020, result.getYear());
        assertEquals("Red", result.getColor());
        assertEquals(new BigDecimal("50000.00"), result.getPrice());
    }

    @Test
    void shouldMapToEntity() {
        // Given
        CarToBuy car = CarToBuy.builder()
                .carToBuyId(1)
                .vin("1FT7X2B60FEA74019")
                .brand("Ford")
                .model("F-150")
                .year(2020)
                .color("Red")
                .price(new BigDecimal("50000.00"))
                .build();

        // When
        CarToBuyEntity result = mapper.mapToEntity(car);

        // Then
        assertNotNull(result);
        assertNull(result.getCarToBuyId()); // ID is ignored when mapping to entity
        assertEquals("1FT7X2B60FEA74019", result.getVin());
        assertEquals("Ford", result.getBrand());
        assertEquals("F-150", result.getModel());
        assertEquals(2020, result.getYear());
        assertEquals("Red", result.getColor());
        assertEquals(new BigDecimal("50000.00"), result.getPrice());
    }

    @Test
    void shouldHandleNullEntity() {
        // When
        CarToBuy result = mapper.mapFromEntity(null);

        // Then
        assertNull(result);
    }

    @Test
    void shouldHandleNullDomain() {
        // When
        CarToBuyEntity result = mapper.mapToEntity(null);

        // Then
        assertNull(result);
    }
}
