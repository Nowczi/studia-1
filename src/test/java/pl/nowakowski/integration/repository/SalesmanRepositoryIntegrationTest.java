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
import pl.nowakowski.infrastructure.database.entity.SalesmanEntity;
import pl.nowakowski.infrastructure.database.repository.jpa.SalesmanJpaRepository;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@Testcontainers
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@ActiveProfiles("test")
class SalesmanRepositoryIntegrationTest {

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
    private SalesmanJpaRepository salesmanJpaRepository;

    @Test
    void shouldSaveAndFindSalesman() {
        // Given
        SalesmanEntity salesman = SalesmanEntity.builder()
                .name("John")
                .surname("Doe")
                .pesel("12345678901")
                .build();

        // When
        SalesmanEntity savedSalesman = salesmanJpaRepository.save(salesman);
        Optional<SalesmanEntity> foundSalesman = salesmanJpaRepository.findById(savedSalesman.getSalesmanId());

        // Then
        assertTrue(foundSalesman.isPresent());
        assertEquals("John", foundSalesman.get().getName());
        assertEquals("Doe", foundSalesman.get().getSurname());
        assertEquals("12345678901", foundSalesman.get().getPesel());
    }

    @Test
    void shouldFindByPesel() {
        // Given
        SalesmanEntity salesman = SalesmanEntity.builder()
                .name("Jane")
                .surname("Smith")
                .pesel("98765432109")
                .build();
        salesmanJpaRepository.save(salesman);

        // When
        Optional<SalesmanEntity> foundSalesman = salesmanJpaRepository.findByPesel("98765432109");

        // Then
        assertTrue(foundSalesman.isPresent());
        assertEquals("Jane", foundSalesman.get().getName());
        assertEquals("Smith", foundSalesman.get().getSurname());
    }
}
