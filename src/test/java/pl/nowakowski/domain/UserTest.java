package pl.nowakowski.domain;

import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class UserTest {

    @Test
    void shouldBuildUserSuccessfully() {
        // Given & When
        User user = User.builder()
                .id(1)
                .userName("johndoe")
                .email("john@example.com")
                .password("password123")
                .active(true)
                .passwordChangeRequired(false)
                .roles(Set.of("SALESMAN"))
                .build();

        // Then
        assertNotNull(user);
        assertEquals(1, user.getId());
        assertEquals("johndoe", user.getUserName());
        assertEquals("john@example.com", user.getEmail());
        assertEquals("password123", user.getPassword());
        assertTrue(user.getActive());
        assertFalse(user.getPasswordChangeRequired());
        assertNotNull(user.getRoles());
        assertEquals(1, user.getRoles().size());
        assertTrue(user.getRoles().contains("SALESMAN"));
    }

    @Test
    void shouldBuildUserWithMultipleRoles() {
        // Given & When
        User user = User.builder()
                .id(1)
                .userName("admin")
                .email("admin@example.com")
                .password("admin123")
                .active(true)
                .roles(Set.of("ADMIN", "SALESMAN", "MECHANIC"))
                .build();

        // Then
        assertNotNull(user.getRoles());
        assertEquals(3, user.getRoles().size());
        assertTrue(user.getRoles().contains("ADMIN"));
        assertTrue(user.getRoles().contains("SALESMAN"));
        assertTrue(user.getRoles().contains("MECHANIC"));
    }

    @Test
    void shouldTestWithMethod() {
        // Given
        User originalUser = User.builder()
                .id(1)
                .userName("johndoe")
                .email("john@example.com")
                .active(true)
                .build();

        // When
        User modifiedUser = originalUser.withEmail("jane@example.com");

        // Then
        assertEquals("jane@example.com", modifiedUser.getEmail());
        assertEquals(originalUser.getUserName(), modifiedUser.getUserName());
    }

    @Test
    void shouldTestEqualsAndHashCodeBasedOnUserName() {
        // Given
        User user1 = User.builder()
                .id(1)
                .userName("johndoe")
                .email("john@example.com")
                .build();

        User user2 = User.builder()
                .id(2)
                .userName("johndoe")
                .email("jane@example.com")
                .build();

        User user3 = User.builder()
                .id(3)
                .userName("janedoe")
                .email("john@example.com")
                .build();

        // Then
        assertEquals(user1, user2);
        assertEquals(user1.hashCode(), user2.hashCode());
        assertNotEquals(user1, user3);
    }

    @Test
    void shouldTestToString() {
        // Given
        User user = User.builder()
                .id(1)
                .userName("johndoe")
                .email("john@example.com")
                .active(true)
                .build();

        // When
        String toString = user.toString();

        // Then
        assertTrue(toString.contains("johndoe"));
        assertTrue(toString.contains("john@example.com"));
        assertTrue(toString.contains("true"));
    }
}
