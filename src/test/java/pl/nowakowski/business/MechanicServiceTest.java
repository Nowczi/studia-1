package pl.nowakowski.business;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import pl.nowakowski.business.dao.MechanicDAO;
import pl.nowakowski.business.dao.UserDAO;
import pl.nowakowski.domain.Mechanic;
import pl.nowakowski.domain.User;
import pl.nowakowski.domain.exception.NotFoundException;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class MechanicServiceTest {

    @Mock
    private MechanicDAO mechanicDAO;

    @Mock
    private UserDAO userDAO;

    @InjectMocks
    private MechanicService mechanicService;

    private Mechanic testMechanic;
    private User testUser;

    @BeforeEach
    void setUp() {
        testMechanic = Mechanic.builder()
                .mechanicId(1)
                .name("John")
                .surname("Doe")
                .pesel("12345678901")
                .userId(100)
                .build();

        testUser = User.builder()
                .id(100)
                .userName("johndoe")
                .email("john@example.com")
                .build();
    }

    @Test
    void shouldFindAvailableMechanics() {
        // Given
        when(mechanicDAO.findAvailable()).thenReturn(List.of(testMechanic));
        when(userDAO.findById(100)).thenReturn(Optional.of(testUser));

        // When
        List<Mechanic> result = mechanicService.findAvailable();

        // Then
        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(testMechanic.getMechanicId(), result.get(0).getMechanicId());
        assertEquals("johndoe", result.get(0).getUserName());
        verify(mechanicDAO, times(1)).findAvailable();
        verify(userDAO, times(1)).findById(100);
    }

    @Test
    void shouldFindAvailableMechanicsWithoutUserId() {
        // Given
        Mechanic mechanicWithoutUserId = Mechanic.builder()
                .mechanicId(2)
                .name("Jane")
                .surname("Smith")
                .pesel("98765432109")
                .build();
        when(mechanicDAO.findAvailable()).thenReturn(List.of(mechanicWithoutUserId));

        // When
        List<Mechanic> result = mechanicService.findAvailable();

        // Then
        assertNotNull(result);
        assertEquals(1, result.size());
        assertNull(result.get(0).getUserName());
        verify(mechanicDAO, times(1)).findAvailable();
        verify(userDAO, never()).findById(any());
    }

    @Test
    void shouldFindMechanicByPesel() {
        // Given
        when(mechanicDAO.findByPesel("12345678901")).thenReturn(Optional.of(testMechanic));
        when(userDAO.findById(100)).thenReturn(Optional.of(testUser));

        // When
        Mechanic result = mechanicService.findMechanic("12345678901");

        // Then
        assertNotNull(result);
        assertEquals(testMechanic.getMechanicId(), result.getMechanicId());
        assertEquals("johndoe", result.getUserName());
        verify(mechanicDAO, times(1)).findByPesel("12345678901");
    }

    @Test
    void shouldThrowNotFoundExceptionWhenMechanicNotFound() {
        // Given
        when(mechanicDAO.findByPesel("00000000000")).thenReturn(Optional.empty());

        // When & Then
        NotFoundException exception = assertThrows(NotFoundException.class, () -> {
            mechanicService.findMechanic("00000000000");
        });
        assertTrue(exception.getMessage().contains("Could not find mechanic by pesel"));
        verify(mechanicDAO, times(1)).findByPesel("00000000000");
    }

    @Test
    void shouldEnrichWithUserNameWhenUserNotFound() {
        // Given
        when(mechanicDAO.findByPesel("12345678901")).thenReturn(Optional.of(testMechanic));
        when(userDAO.findById(100)).thenReturn(Optional.empty());

        // When
        Mechanic result = mechanicService.findMechanic("12345678901");

        // Then
        assertNotNull(result);
        assertNull(result.getUserName());
    }
}
