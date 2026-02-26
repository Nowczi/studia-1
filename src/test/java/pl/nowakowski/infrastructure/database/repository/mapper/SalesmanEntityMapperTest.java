package pl.nowakowski.infrastructure.database.repository.mapper;

import org.junit.jupiter.api.Test;
import pl.nowakowski.domain.Salesman;
import pl.nowakowski.infrastructure.database.entity.SalesmanEntity;

import static org.junit.jupiter.api.Assertions.*;

class SalesmanEntityMapperTest {

    private final SalesmanEntityMapper mapper = new SalesmanEntityMapperImpl();

    @Test
    void shouldMapFromEntity() {
        // Given
        SalesmanEntity entity = SalesmanEntity.builder()
                .salesmanId(1)
                .name("John")
                .surname("Doe")
                .pesel("12345678901")
                .build();

        // When
        Salesman result = mapper.mapFromEntity(entity);

        // Then
        assertNotNull(result);
        assertEquals(1, result.getSalesmanId());
        assertEquals("John", result.getName());
        assertEquals("Doe", result.getSurname());
        assertEquals("12345678901", result.getPesel());
    }

    @Test
    void shouldMapToEntity() {
        // Given
        Salesman salesman = Salesman.builder()
                .salesmanId(1)
                .name("John")
                .surname("Doe")
                .pesel("12345678901")
                .build();

        // When
        SalesmanEntity result = mapper.mapToEntity(salesman);

        // Then
        assertNotNull(result);
        assertEquals("John", result.getName());
        assertEquals("Doe", result.getSurname());
        assertEquals("12345678901", result.getPesel());
    }

    @Test
    void shouldHandleNullEntity() {
        // When
        Salesman result = mapper.mapFromEntity(null);

        // Then
        assertNull(result);
    }

    @Test
    void shouldHandleNullDomain() {
        // When
        SalesmanEntity result = mapper.mapToEntity(null);

        // Then
        assertNull(result);
    }
}
