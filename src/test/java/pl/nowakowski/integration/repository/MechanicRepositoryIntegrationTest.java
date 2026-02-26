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
import pl.nowakowski.infrastructure.database.entity.MechanicEntity;
import pl.nowakowski.infrastructure.database.repository.jpa.MechanicJpaRepository;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@Testcontainers
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@ActiveProfiles("test")
class MechanicRepositoryIntegrationTest {

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
    private MechanicJpaRepository mechanicJpaRepository;

    @Test
    void shouldSaveAndFindMechanic() {
        // Given
        MechanicEntity mechanic = MechanicEntity.builder()
                .name("John")
                .surname("Doe")
                .pesel("12345678901")
                .build();

        // When
        MechanicEntity savedMechanic = mechanicJpaRepository.save(mechanic);
        Optional<MechanicEntity> foundMechanic = mechanicJpaRepository.findById(savedMechanic.getMechanicId());

        // Then
        assertTrue(foundMechanic.isPresent());
        assertEquals("John", foundMechanic.get().getName());
        assertEquals("Doe", foundMechanic.get().getSurname());
        assertEquals("12345678901", foundMechanic.get().getPesel());
    }

    @Test
    void shouldFindByPesel() {
        // Given
        MechanicEntity mechanic = MechanicEntity.builder()
                .name("Jane")
                .surname("Smith")
                .pesel("98765432109")
                .build();
        mechanicJpaRepository.save(mechanic);

        // When
        Optional<MechanicEntity> foundMechanic = mechanicJpaRepository.findByPesel("98765432109");

        // Then
        assertTrue(foundMechanic.isPresent());
        assertEquals("Jane", foundMechanic.get().getName());
        assertEquals("Smith", foundMechanic.get().getSurname());
    }
}
