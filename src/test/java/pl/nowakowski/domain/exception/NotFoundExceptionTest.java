package pl.nowakowski.domain.exception;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class NotFoundExceptionTest {

    @Test
    void shouldCreateNotFoundExceptionWithMessage() {
        // Given
        String message = "Resource not found";

        // When
        NotFoundException exception = new NotFoundException(message);

        // Then
        assertNotNull(exception);
        assertEquals(message, exception.getMessage());
    }

    @Test
    void shouldCreateNotFoundExceptionWithFormattedMessage() {
        // Given
        String resourceType = "Car";
        String identifier = "VIN123";

        // When
        NotFoundException exception = new NotFoundException(
                "Could not find %s by identifier: [%s]".formatted(resourceType, identifier)
        );

        // Then
        assertNotNull(exception);
        assertEquals("Could not find Car by identifier: [VIN123]", exception.getMessage());
    }

    @Test
    void shouldBeInstanceOfRuntimeException() {
        // Given
        NotFoundException exception = new NotFoundException("Test message");

        // Then
        assertTrue(exception instanceof RuntimeException);
    }
}
