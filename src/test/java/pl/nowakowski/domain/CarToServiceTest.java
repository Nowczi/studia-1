package pl.nowakowski.domain;

import org.junit.jupiter.api.Test;

import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class CarToServiceTest {

    @Test
    void shouldBuildCarToServiceSuccessfully() {
        // Given & When
        CarToService car = CarToService.builder()
                .carToServiceId(1)
                .vin("1FT7X2B60FEA74019")
                .brand("Ford")
                .model("F-150")
                .year(2020)
                .build();

        // Then
        assertNotNull(car);
        assertEquals(1, car.getCarToServiceId());
        assertEquals("1FT7X2B60FEA74019", car.getVin());
        assertEquals("Ford", car.getBrand());
        assertEquals("F-150", car.getModel());
        assertEquals(2020, car.getYear());
    }

    @Test
    void shouldBuildCarToServiceWithServiceRequests() {
        // Given
        CarServiceRequest request1 = CarServiceRequest.builder()
                .carServiceRequestId(1)
                .carServiceRequestNumber("2024.1.1-10.30.0.50")
                .build();

        CarServiceRequest request2 = CarServiceRequest.builder()
                .carServiceRequestId(2)
                .carServiceRequestNumber("2024.1.2-11.00.0.60")
                .build();

        Set<CarServiceRequest> requests = new HashSet<>();
        requests.add(request1);
        requests.add(request2);

        // When
        CarToService car = CarToService.builder()
                .carToServiceId(1)
                .vin("1FT7X2B60FEA74019")
                .brand("Ford")
                .model("F-150")
                .year(2020)
                .carServiceRequests(requests)
                .build();

        // Then
        assertNotNull(car.getCarServiceRequests());
        assertEquals(2, car.getCarServiceRequests().size());
    }

    @Test
    void shouldTestWithMethod() {
        // Given
        CarToService originalCar = CarToService.builder()
                .vin("1FT7X2B60FEA74019")
                .brand("Ford")
                .model("F-150")
                .year(2020)
                .build();

        // When
        CarToService modifiedCar = originalCar.withBrand("Toyota");

        // Then
        assertEquals("Toyota", modifiedCar.getBrand());
        assertEquals(originalCar.getVin(), modifiedCar.getVin());
    }

    @Test
    void shouldTestEqualsAndHashCodeBasedOnVin() {
        // Given
        CarToService car1 = CarToService.builder()
                .carToServiceId(1)
                .vin("1FT7X2B60FEA74019")
                .brand("Ford")
                .model("F-150")
                .build();

        CarToService car2 = CarToService.builder()
                .carToServiceId(2)
                .vin("1FT7X2B60FEA74019")
                .brand("Toyota")
                .model("Camry")
                .build();

        CarToService car3 = CarToService.builder()
                .carToServiceId(3)
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
        CarToService car = CarToService.builder()
                .carToServiceId(1)
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

    @Test
    void shouldReturnTrueWhenCarShouldExistInCarToBuy() {
        // Given
        CarToService car = CarToService.builder()
                .vin("1FT7X2B60FEA74019")
                .build();

        // When & Then
        assertTrue(car.shouldExistsInCarToBuy());
    }

    @Test
    void shouldReturnFalseWhenCarHasBrand() {
        // Given
        CarToService car = CarToService.builder()
                .vin("1FT7X2B60FEA74019")
                .brand("Ford")
                .build();

        // When & Then
        assertFalse(car.shouldExistsInCarToBuy());
    }

    @Test
    void shouldReturnFalseWhenCarHasModel() {
        // Given
        CarToService car = CarToService.builder()
                .vin("1FT7X2B60FEA74019")
                .model("F-150")
                .build();

        // When & Then
        assertFalse(car.shouldExistsInCarToBuy());
    }

    @Test
    void shouldReturnFalseWhenCarHasYear() {
        // Given
        CarToService car = CarToService.builder()
                .vin("1FT7X2B60FEA74019")
                .year(2020)
                .build();

        // When & Then
        assertFalse(car.shouldExistsInCarToBuy());
    }

    @Test
    void shouldReturnFalseWhenVinIsNull() {
        // Given
        CarToService car = CarToService.builder()
                .brand("Ford")
                .build();

        // When & Then
        assertFalse(car.shouldExistsInCarToBuy());
    }
}
