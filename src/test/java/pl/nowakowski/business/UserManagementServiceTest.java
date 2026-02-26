package pl.nowakowski.business;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;
import pl.nowakowski.business.dao.MechanicDAO;
import pl.nowakowski.business.dao.SalesmanDAO;
import pl.nowakowski.business.dao.UserDAO;
import pl.nowakowski.domain.Mechanic;
import pl.nowakowski.domain.Salesman;
import pl.nowakowski.domain.User;
import pl.nowakowski.domain.exception.NotFoundException;
import pl.nowakowski.domain.exception.ProcessingException;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserManagementServiceTest {

    @Mock
    private UserDAO userDAO;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private MechanicDAO mechanicDAO;

    @Mock
    private SalesmanDAO salesmanDAO;

    @InjectMocks
    private UserManagementService userManagementService;

    private User testUser;
    private User adminUser;

    @BeforeEach
    void setUp() {
        testUser = User.builder()
                .id(1)
                .userName("johndoe")
                .email("john@example.com")
                .password("password123")
                .active(true)
                .passwordChangeRequired(false)
                .roles(Set.of("SALESMAN"))
                .build();

        adminUser = User.builder()
                .id(999)
                .userName("admin")
                .email("admin@example.com")
                .password("admin123")
                .active(true)
                .roles(Set.of("ADMIN"))
                .build();
    }

    @Test
    void shouldFindAllUsers() {
        // Given
        when(userDAO.findAll()).thenReturn(List.of(testUser));

        // When
        List<User> result = userManagementService.findAllUsers();

        // Then
        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(testUser, result.get(0));
        verify(userDAO, times(1)).findAll();
    }

    @Test
    void shouldFindUserByUserName() {
        // Given
        when(userDAO.findByUserName("johndoe")).thenReturn(Optional.of(testUser));

        // When
        User result = userManagementService.findUser("johndoe");

        // Then
        assertNotNull(result);
        assertEquals(testUser, result);
        verify(userDAO, times(1)).findByUserName("johndoe");
    }

    @Test
    void shouldThrowNotFoundExceptionWhenUserNotFound() {
        // Given
        when(userDAO.findByUserName("nonexistent")).thenReturn(Optional.empty());

        // When & Then
        NotFoundException exception = assertThrows(NotFoundException.class, () -> {
            userManagementService.findUser("nonexistent");
        });
        assertTrue(exception.getMessage().contains("Could not find user by username"));
    }

    @Test
    void shouldCreateUserWithSalesmanRole() {
        // Given
        User newUser = User.builder()
                .userName("newuser")
                .email("new@example.com")
                .password("plainpassword")
                .build();

        User savedUser = User.builder()
                .id(2)
                .userName("newuser")
                .email("new@example.com")
                .password("encodedpassword")
                .active(true)
                .roles(Set.of("SALESMAN"))
                .build();

        when(userDAO.existsByUserName("newuser")).thenReturn(false);
        when(userDAO.existsByEmail("new@example.com")).thenReturn(false);
        when(passwordEncoder.encode("plainpassword")).thenReturn("encodedpassword");
        when(userDAO.save(any(User.class))).thenReturn(savedUser);
        doNothing().when(salesmanDAO).save(any(Salesman.class));

        // When
        User result = userManagementService.createUser(newUser, "SALESMAN", "John", "Doe", "12345678901");

        // Then
        assertNotNull(result);
        assertEquals("newuser", result.getUserName());
        assertEquals("encodedpassword", result.getPassword());
        assertTrue(result.getActive());
        verify(userDAO, times(1)).save(any(User.class));
        verify(salesmanDAO, times(1)).save(any(Salesman.class));
        verify(mechanicDAO, never()).save(any(Mechanic.class));
    }

    @Test
    void shouldCreateUserWithMechanicRole() {
        // Given
        User newUser = User.builder()
                .userName("newmechanic")
                .email("mechanic@example.com")
                .password("plainpassword")
                .build();

        User savedUser = User.builder()
                .id(2)
                .userName("newmechanic")
                .email("mechanic@example.com")
                .password("encodedpassword")
                .active(true)
                .roles(Set.of("MECHANIC"))
                .build();

        when(userDAO.existsByUserName("newmechanic")).thenReturn(false);
        when(userDAO.existsByEmail("mechanic@example.com")).thenReturn(false);
        when(passwordEncoder.encode("plainpassword")).thenReturn("encodedpassword");
        when(userDAO.save(any(User.class))).thenReturn(savedUser);
        doNothing().when(mechanicDAO).save(any(Mechanic.class));

        // When
        User result = userManagementService.createUser(newUser, "MECHANIC", "Jane", "Smith", "98765432109");

        // Then
        assertNotNull(result);
        verify(userDAO, times(1)).save(any(User.class));
        verify(mechanicDAO, times(1)).save(any(Mechanic.class));
        verify(salesmanDAO, never()).save(any(Salesman.class));
    }

    @Test
    void shouldThrowProcessingExceptionWhenUserNameExists() {
        // Given
        User newUser = User.builder()
                .userName("existinguser")
                .email("new@example.com")
                .password("password")
                .build();

        when(userDAO.existsByUserName("existinguser")).thenReturn(true);

        // When & Then
        ProcessingException exception = assertThrows(ProcessingException.class, () -> {
            userManagementService.createUser(newUser, "SALESMAN", "John", "Doe", "12345678901");
        });
        assertTrue(exception.getMessage().contains("already exists"));
    }

    @Test
    void shouldThrowProcessingExceptionWhenEmailExists() {
        // Given
        User newUser = User.builder()
                .userName("newuser")
                .email("existing@example.com")
                .password("password")
                .build();

        when(userDAO.existsByUserName("newuser")).thenReturn(false);
        when(userDAO.existsByEmail("existing@example.com")).thenReturn(true);

        // When & Then
        ProcessingException exception = assertThrows(ProcessingException.class, () -> {
            userManagementService.createUser(newUser, "SALESMAN", "John", "Doe", "12345678901");
        });
        assertTrue(exception.getMessage().contains("already exists"));
    }

    @Test
    void shouldDeleteUser() {
        // Given
        when(userDAO.findAll()).thenReturn(List.of(testUser));
        doNothing().when(mechanicDAO).deleteByUserId(1);
        doNothing().when(salesmanDAO).deleteByUserId(1);
        doNothing().when(userDAO).deleteById(1);

        // When
        userManagementService.deleteUser(1);

        // Then
        verify(mechanicDAO, times(1)).deleteByUserId(1);
        verify(salesmanDAO, times(1)).deleteByUserId(1);
        verify(userDAO, times(1)).deleteById(1);
    }

    @Test
    void shouldThrowProcessingExceptionWhenDeletingAdmin() {
        // Given
        when(userDAO.findAll()).thenReturn(List.of(adminUser));

        // When & Then
        ProcessingException exception = assertThrows(ProcessingException.class, () -> {
            userManagementService.deleteUser(999);
        });
        assertTrue(exception.getMessage().contains("Cannot delete the admin user"));
    }

    @Test
    void shouldToggleUserActive() {
        // Given
        when(userDAO.findAll()).thenReturn(List.of(testUser));
        when(userDAO.save(any(User.class))).thenReturn(testUser.withActive(false));

        // When
        User result = userManagementService.toggleUserActive(1);

        // Then
        assertNotNull(result);
        assertFalse(result.getActive());
        verify(userDAO, times(1)).save(any(User.class));
    }

    @Test
    void shouldThrowProcessingExceptionWhenDeactivatingAdmin() {
        // Given
        when(userDAO.findAll()).thenReturn(List.of(adminUser));

        // When & Then
        ProcessingException exception = assertThrows(ProcessingException.class, () -> {
            userManagementService.toggleUserActive(999);
        });
        assertTrue(exception.getMessage().contains("Cannot deactivate the admin user"));
    }

    @Test
    void shouldResetUserPassword() {
        // Given
        String newPassword = "newpassword123";
        String encodedPassword = "encodednewpassword";

        when(userDAO.findAll()).thenReturn(List.of(testUser));
        when(passwordEncoder.encode(newPassword)).thenReturn(encodedPassword);
        when(userDAO.resetPassword(1, encodedPassword)).thenReturn(testUser.withPassword(encodedPassword));

        // When
        User result = userManagementService.resetUserPassword(1, newPassword);

        // Then
        assertNotNull(result);
        assertEquals(encodedPassword, result.getPassword());
        verify(passwordEncoder, times(1)).encode(newPassword);
        verify(userDAO, times(1)).resetPassword(1, encodedPassword);
    }

    @Test
    void shouldThrowProcessingExceptionWhenResettingAdminPassword() {
        // Given
        when(userDAO.findAll()).thenReturn(List.of(adminUser));

        // When & Then
        ProcessingException exception = assertThrows(ProcessingException.class, () -> {
            userManagementService.resetUserPassword(999, "newpassword");
        });
        assertTrue(exception.getMessage().contains("Cannot reset password for the admin user"));
    }

    @Test
    void shouldChangeUserPassword() {
        // Given
        String newPassword = "newpassword123";
        String encodedPassword = "encodednewpassword";

        when(userDAO.findAll()).thenReturn(List.of(testUser));
        when(passwordEncoder.encode(newPassword)).thenReturn(encodedPassword);
        when(userDAO.changePassword(1, encodedPassword)).thenReturn(testUser.withPassword(encodedPassword));

        // When
        User result = userManagementService.changeUserPassword(1, newPassword);

        // Then
        assertNotNull(result);
        assertEquals(encodedPassword, result.getPassword());
        verify(passwordEncoder, times(1)).encode(newPassword);
        verify(userDAO, times(1)).changePassword(1, encodedPassword);
    }

    @Test
    void shouldReturnTrueWhenPasswordChangeRequired() {
        // Given
        User userWithPasswordChangeRequired = testUser.withPasswordChangeRequired(true);
        when(userDAO.findByUserName("johndoe")).thenReturn(Optional.of(userWithPasswordChangeRequired));

        // When
        boolean result = userManagementService.isPasswordChangeRequired("johndoe");

        // Then
        assertTrue(result);
    }

    @Test
    void shouldReturnFalseWhenPasswordChangeNotRequired() {
        // Given
        when(userDAO.findByUserName("johndoe")).thenReturn(Optional.of(testUser));

        // When
        boolean result = userManagementService.isPasswordChangeRequired("johndoe");

        // Then
        assertFalse(result);
    }

    @Test
    void shouldReturnAvailableRoles() {
        // When
        Set<String> result = userManagementService.getAvailableRoles();

        // Then
        assertNotNull(result);
        assertEquals(4, result.size());
        assertTrue(result.contains("SALESMAN"));
        assertTrue(result.contains("MECHANIC"));
        assertTrue(result.contains("REST_API"));
        assertTrue(result.contains("ADMIN"));
    }
}
