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
import pl.nowakowski.infrastructure.database.entity.ServiceEntity;
import pl.nowakowski.infrastructure.database.repository.jpa.ServiceJpaRepository;

import java.math.BigDecimal;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@Testcontainers
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@ActiveProfiles("test")
class ServiceRepositoryIntegrationTest {

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
    private ServiceJpaRepository serviceJpaRepository;

    @Test
    void shouldSaveAndFindService() {
        // Given
        ServiceEntity service = ServiceEntity.builder()
                .serviceCode("SRV-001")
                .description("Oil change")
                .price(new BigDecimal("100.00"))
                .build();

        // When
        ServiceEntity savedService = serviceJpaRepository.save(service);
        Optional<ServiceEntity> foundService = serviceJpaRepository.findById(savedService.getServiceId());

        // Then
        assertTrue(foundService.isPresent());
        assertEquals("SRV-001", foundService.get().getServiceCode());
        assertEquals("Oil change", foundService.get().getDescription());
        assertEquals(new BigDecimal("100.00"), foundService.get().getPrice());
    }

    @Test
    void shouldFindByServiceCode() {
        // Given
        ServiceEntity service = ServiceEntity.builder()
                .serviceCode("SRV-002")
                .description("Brake inspection")
                .price(new BigDecimal("50.00"))
                .build();
        serviceJpaRepository.save(service);

        // When
        Optional<ServiceEntity> foundService = serviceJpaRepository.findByServiceCode("SRV-002");

        // Then
        assertTrue(foundService.isPresent());
        assertEquals("Brake inspection", foundService.get().getDescription());
    }
}
