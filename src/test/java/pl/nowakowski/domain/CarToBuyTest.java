package pl.nowakowski.domain;

import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

class CarToBuyTest {

    @Test
    void shouldBuildCarToBuySuccessfully() {
        // Given & When
        CarToBuy car = CarToBuy.builder()
                .carToBuyId(1)
                .vin("1FT7X2B60FEA74019")
                .brand("Ford")
                .model("F-150")
                .year(2020)
                .color("Red")
                .price(new BigDecimal("50000.00"))
                .build();

        // Then
        assertNotNull(car);
        assertEquals(1, car.getCarToBuyId());
        assertEquals("1FT7X2B60FEA74019", car.getVin());
        assertEquals("Ford", car.getBrand());
        assertEquals("F-150", car.getModel());
        assertEquals(2020, car.getYear());
        assertEquals("Red", car.getColor());
        assertEquals(new BigDecimal("50000.00"), car.getPrice());
    }

    @Test
    void shouldTestWithMethod() {
        // Given
        CarToBuy originalCar = CarToBuy.builder()
                .vin("1FT7X2B60FEA74019")
                .brand("Ford")
                .model("F-150")
                .year(2020)
                .build();

        // When
        CarToBuy modifiedCar = originalCar.withPrice(new BigDecimal("55000.00"));

        // Then
        assertEquals(new BigDecimal("55000.00"), modifiedCar.getPrice());
        assertEquals(originalCar.getVin(), modifiedCar.getVin());
        assertEquals(originalCar.getBrand(), modifiedCar.getBrand());
    }

    @Test
    void shouldTestEqualsAndHashCodeBasedOnVin() {
        // Given
        CarToBuy car1 = CarToBuy.builder()
                .carToBuyId(1)
                .vin("1FT7X2B60FEA74019")
                .brand("Ford")
                .model("F-150")
                .build();

        CarToBuy car2 = CarToBuy.builder()
                .carToBuyId(2)
                .vin("1FT7X2B60FEA74019")
                .brand("Toyota")
                .model("Camry")
                .build();

        CarToBuy car3 = CarToBuy.builder()
                .carToBuyId(3)
                .vin("WBAWL73589P473201")
                .brand("BMW")
                .model("X5")
                .build();

        // Then
        assertEquals(car1, car2);
        assertEquals(car1.hashCode(), car2.hashCode());
        assertNotEquals(car1, car3);
    }

    @Test
    void shouldTestToString() {
        // Given
        CarToBuy car = CarToBuy.builder()
                .carToBuyId(1)
                .vin("1FT7X2B60FEA74019")
                .brand("Ford")
                .model("F-150")
                .year(2020)
                .build();

        // When
        String toString = car.toString();

        // Then
        assertTrue(toString.contains("Ford"));
        assertTrue(toString.contains("F-150"));
        assertTrue(toString.contains("2020"));
    }
}
