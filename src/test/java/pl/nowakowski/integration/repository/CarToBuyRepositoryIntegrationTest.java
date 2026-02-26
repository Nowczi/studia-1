package pl.nowakowski.integration.repository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import pl.nowakowski.infrastructure.database.entity.CarToBuyEntity;
import pl.nowakowski.infrastructure.database.repository.jpa.CarToBuyJpaRepository;

import java.math.BigDecimal;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@Testcontainers
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@ActiveProfiles("test")
class CarToBuyRepositoryIntegrationTest {

    @Container
    static PostgreSQLContainer<?> postgres = new PostgreSQLContainer<>("postgres:15-alpine")
            .withDatabaseName("car_dealership_test")
            .withUsername("test")
            .withPassword("test");

    @DynamicPropertySource
    static void configureProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", postgres::getJdbcUrl);
        registry.add("spring.datasource.username", postgres::getUsername);
        registry.add("spring.datasource.password", postgres::getPassword);
    }

    @Autowired
    private CarToBuyJpaRepository carToBuyJpaRepository;

    @Test
    void shouldSaveAndFindCarToBuy() {
        // Given
        CarToBuyEntity car = CarToBuyEntity.builder()
                .vin("1FT7X2B60FEA74019")
                .brand("Ford")
                .model("F-150")
                .year(2020)
                .color("Red")
                .price(new BigDecimal("50000.00"))
                .build();

        // When
        CarToBuyEntity savedCar = carToBuyJpaRepository.save(car);
        Optional<CarToBuyEntity> foundCar = carToBuyJpaRepository.findById(savedCar.getCarToBuyId());

        // Then
        assertTrue(foundCar.isPresent());
        assertEquals("1FT7X2B60FEA74019", foundCar.get().getVin());
        assertEquals("Ford", foundCar.get().getBrand());
        assertEquals("F-150", foundCar.get().getModel());
        assertEquals(2020, foundCar.get().getYear());
    }

    @Test
    void shouldFindByVin() {
        // Given
        CarToBuyEntity car = CarToBuyEntity.builder()
                .vin("WBAWL73589P473201")
                .brand("BMW")
                .model("X5")
                .year(2021)
                .color("Black")
                .price(new BigDecimal("80000.00"))
                .build();
        carToBuyJpaRepository.save(car);

        // When
        Optional<CarToBuyEntity> foundCar = carToBuyJpaRepository.findByVin("WBAWL73589P473201");

        // Then
        assertTrue(foundCar.isPresent());
        assertEquals("BMW", foundCar.get().getBrand());
    }
}
