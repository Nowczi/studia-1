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
import pl.nowakowski.infrastructure.database.entity.AddressEntity;
import pl.nowakowski.infrastructure.database.entity.CustomerEntity;
import pl.nowakowski.infrastructure.database.repository.jpa.CustomerJpaRepository;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@Testcontainers
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@ActiveProfiles("test")
class CustomerRepositoryIntegrationTest {

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
    private CustomerJpaRepository customerJpaRepository;

    @Test
    void shouldSaveAndFindCustomer() {
        // Given
        AddressEntity address = AddressEntity.builder()
                .country("Poland")
                .city("Warsaw")
                .postalCode("00-001")
                .address("Main Street 123")
                .build();

        CustomerEntity customer = CustomerEntity.builder()
                .name("John")
                .surname("Doe")
                .phone("+48 123 456 789")
                .email("john@example.com")
                .address(address)
                .build();

        // When
        CustomerEntity savedCustomer = customerJpaRepository.save(customer);
        Optional<CustomerEntity> foundCustomer = customerJpaRepository.findById(savedCustomer.getCustomerId());

        // Then
        assertTrue(foundCustomer.isPresent());
        assertEquals("John", foundCustomer.get().getName());
        assertEquals("Doe", foundCustomer.get().getSurname());
        assertEquals("john@example.com", foundCustomer.get().getEmail());
    }

    @Test
    void shouldFindByEmail() {
        // Given
        CustomerEntity customer = CustomerEntity.builder()
                .name("Jane")
                .surname("Smith")
                .phone("+48 987 654 321")
                .email("jane@example.com")
                .build();
        customerJpaRepository.save(customer);

        // When
        Optional<CustomerEntity> foundCustomer = customerJpaRepository.findByEmail("jane@example.com");

        // Then
        assertTrue(foundCustomer.isPresent());
        assertEquals("Jane", foundCustomer.get().getName());
        assertEquals("Smith", foundCustomer.get().getSurname());
    }
}
