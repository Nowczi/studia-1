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
import pl.nowakowski.infrastructure.database.entity.PartEntity;
import pl.nowakowski.infrastructure.database.repository.jpa.PartJpaRepository;

import java.math.BigDecimal;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@Testcontainers
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@ActiveProfiles("test")
class PartRepositoryIntegrationTest {

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
    private PartJpaRepository partJpaRepository;

    @Test
    void shouldSaveAndFindPart() {
        // Given
        PartEntity part = PartEntity.builder()
                .serialNumber("PART-001")
                .description("Brake pads")
                .price(new BigDecimal("150.00"))
                .build();

        // When
        PartEntity savedPart = partJpaRepository.save(part);
        Optional<PartEntity> foundPart = partJpaRepository.findById(savedPart.getPartId());

        // Then
        assertTrue(foundPart.isPresent());
        assertEquals("PART-001", foundPart.get().getSerialNumber());
        assertEquals("Brake pads", foundPart.get().getDescription());
        assertEquals(new BigDecimal("150.00"), foundPart.get().getPrice());
    }

    @Test
    void shouldFindBySerialNumber() {
        // Given
        PartEntity part = PartEntity.builder()
                .serialNumber("PART-002")
                .description("Oil filter")
                .price(new BigDecimal("25.00"))
                .build();
        partJpaRepository.save(part);

        // When
        Optional<PartEntity> foundPart = partJpaRepository.findBySerialNumber("PART-002");

        // Then
        assertTrue(foundPart.isPresent());
        assertEquals("Oil filter", foundPart.get().getDescription());
    }
}
