package pl.nowakowski.business;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import pl.nowakowski.business.dao.SalesmanDAO;
import pl.nowakowski.business.dao.UserDAO;
import pl.nowakowski.domain.Salesman;
import pl.nowakowski.domain.User;
import pl.nowakowski.domain.exception.NotFoundException;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class SalesmanServiceTest {

    @Mock
    private SalesmanDAO salesmanDAO;

    @Mock
    private UserDAO userDAO;

    @InjectMocks
    private SalesmanService salesmanService;

    private Salesman testSalesman;
    private User testUser;

    @BeforeEach
    void setUp() {
        testSalesman = Salesman.builder()
                .salesmanId(1)
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
    void shouldFindAvailableSalesmen() {
        // Given
        when(salesmanDAO.findAvailable()).thenReturn(List.of(testSalesman));
        when(userDAO.findById(100)).thenReturn(Optional.of(testUser));

        // When
        List<Salesman> result = salesmanService.findAvailable();

        // Then
        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(testSalesman.getSalesmanId(), result.get(0).getSalesmanId());
        assertEquals("johndoe", result.get(0).getUserName());
        verify(salesmanDAO, times(1)).findAvailable();
        verify(userDAO, times(1)).findById(100);
    }

    @Test
    void shouldFindAvailableSalesmenWithoutUserId() {
        // Given
        Salesman salesmanWithoutUserId = Salesman.builder()
                .salesmanId(2)
                .name("Jane")
                .surname("Smith")
                .pesel("98765432109")
                .build();
        when(salesmanDAO.findAvailable()).thenReturn(List.of(salesmanWithoutUserId));

        // When
        List<Salesman> result = salesmanService.findAvailable();

        // Then
        assertNotNull(result);
        assertEquals(1, result.size());
        assertNull(result.get(0).getUserName());
        verify(salesmanDAO, times(1)).findAvailable();
        verify(userDAO, never()).findById(any());
    }

    @Test
    void shouldFindSalesmanByPesel() {
        // Given
        when(salesmanDAO.findByPesel("12345678901")).thenReturn(Optional.of(testSalesman));
        when(userDAO.findById(100)).thenReturn(Optional.of(testUser));

        // When
        Salesman result = salesmanService.findSalesman("12345678901");

        // Then
        assertNotNull(result);
        assertEquals(testSalesman.getSalesmanId(), result.getSalesmanId());
        assertEquals("johndoe", result.getUserName());
        verify(salesmanDAO, times(1)).findByPesel("12345678901");
    }

    @Test
    void shouldThrowNotFoundExceptionWhenSalesmanNotFound() {
        // Given
        when(salesmanDAO.findByPesel("00000000000")).thenReturn(Optional.empty());

        // When & Then
        NotFoundException exception = assertThrows(NotFoundException.class, () -> {
            salesmanService.findSalesman("00000000000");
        });
        assertTrue(exception.getMessage().contains("Could not find salesman by pesel"));
        verify(salesmanDAO, times(1)).findByPesel("00000000000");
    }

    @Test
    void shouldEnrichWithUserNameWhenUserNotFound() {
        // Given
        when(salesmanDAO.findByPesel("12345678901")).thenReturn(Optional.of(testSalesman));
        when(userDAO.findById(100)).thenReturn(Optional.empty());

        // When
        Salesman result = salesmanService.findSalesman("12345678901");

        // Then
        assertNotNull(result);
        assertNull(result.getUserName());
    }
}
