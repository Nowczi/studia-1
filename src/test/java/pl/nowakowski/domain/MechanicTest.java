package pl.nowakowski.domain;

import org.junit.jupiter.api.Test;

import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class MechanicTest {

    @Test
    void shouldBuildMechanicSuccessfully() {
        // Given & When
        Mechanic mechanic = Mechanic.builder()
                .mechanicId(1)
                .name("John")
                .surname("Doe")
                .pesel("12345678901")
                .userId(100)
                .userName("johndoe")
                .build();

        // Then
        assertNotNull(mechanic);
        assertEquals(1, mechanic.getMechanicId());
        assertEquals("John", mechanic.getName());
        assertEquals("Doe", mechanic.getSurname());
        assertEquals("12345678901", mechanic.getPesel());
        assertEquals(100, mechanic.getUserId());
        assertEquals("johndoe", mechanic.getUserName());
    }

    @Test
    void shouldBuildMechanicWithServiceMechanics() {
        // Given
        ServiceMechanic serviceMechanic = ServiceMechanic.builder()
                .serviceMechanicId(1)
                .hours(5)
                .comment("Oil change")
                .build();

        Set<ServiceMechanic> serviceMechanics = new HashSet<>();
        serviceMechanics.add(serviceMechanic);

        // When
        Mechanic mechanic = Mechanic.builder()
                .mechanicId(1)
                .name("John")
                .surname("Doe")
                .pesel("12345678901")
                .serviceMechanics(serviceMechanics)
                .build();

        // Then
        assertNotNull(mechanic.getServiceMechanics());
        assertEquals(1, mechanic.getServiceMechanics().size());
    }

    @Test
    void shouldTestWithMethod() {
        // Given
        Mechanic originalMechanic = Mechanic.builder()
                .mechanicId(1)
                .name("John")
                .surname("Doe")
                .pesel("12345678901")
                .build();

        // When
        Mechanic modifiedMechanic = originalMechanic.withName("Jane");

        // Then
        assertEquals("Jane", modifiedMechanic.getName());
        assertEquals(originalMechanic.getSurname(), modifiedMechanic.getSurname());
    }

    @Test
    void shouldTestEqualsAndHashCodeBasedOnPesel() {
        // Given
        Mechanic mechanic1 = Mechanic.builder()
                .mechanicId(1)
                .name("John")
                .surname("Doe")
                .pesel("12345678901")
                .build();

        Mechanic mechanic2 = Mechanic.builder()
                .mechanicId(2)
                .name("Jane")
                .surname("Smith")
                .pesel("12345678901")
                .build();

        Mechanic mechanic3 = Mechanic.builder()
                .mechanicId(3)
                .name("Bob")
                .surname("Wilson")
                .pesel("98765432109")
                .build();

        // Then
        assertEquals(mechanic1, mechanic2);
        assertEquals(mechanic1.hashCode(), mechanic2.hashCode());
        assertNotEquals(mechanic1, mechanic3);
    }

    @Test
    void shouldTestToString() {
        // Given
        Mechanic mechanic = Mechanic.builder()
                .mechanicId(1)
                .name("John")
                .surname("Doe")
                .pesel("12345678901")
                .build();

        // When
        String toString = mechanic.toString();

        // Then
        assertTrue(toString.contains("John"));
        assertTrue(toString.contains("Doe"));
        assertTrue(toString.contains("12345678901"));
    }
}
