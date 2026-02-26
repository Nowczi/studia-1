package pl.nowakowski.infrastructure.database.repository.mapper;

import org.junit.jupiter.api.Test;
import pl.nowakowski.domain.Mechanic;
import pl.nowakowski.infrastructure.database.entity.MechanicEntity;

import static org.junit.jupiter.api.Assertions.*;

class MechanicEntityMapperTest {

    private final MechanicEntityMapper mapper = new MechanicEntityMapperImpl();

    @Test
    void shouldMapFromEntity() {
        // Given
        MechanicEntity entity = MechanicEntity.builder()
                .mechanicId(1)
                .name("John")
                .surname("Doe")
                .pesel("12345678901")
                .build();

        // When
        Mechanic result = mapper.mapFromEntity(entity);

        // Then
        assertNotNull(result);
        assertEquals(1, result.getMechanicId());
        assertEquals("John", result.getName());
        assertEquals("Doe", result.getSurname());
        assertEquals("12345678901", result.getPesel());
    }

    @Test
    void shouldMapToEntity() {
        // Given
        Mechanic mechanic = Mechanic.builder()
                .mechanicId(1)
                .name("John")
                .surname("Doe")
                .pesel("12345678901")
                .build();

        // When
        MechanicEntity result = mapper.mapToEntity(mechanic);

        // Then
        assertNotNull(result);
        assertEquals("John", result.getName());
        assertEquals("Doe", result.getSurname());
        assertEquals("12345678901", result.getPesel());
    }

    @Test
    void shouldHandleNullEntity() {
        // When
        Mechanic result = mapper.mapFromEntity(null);

        // Then
        assertNull(result);
    }

    @Test
    void shouldHandleNullDomain() {
        // When
        MechanicEntity result = mapper.mapToEntity(null);

        // Then
        assertNull(result);
    }
}
