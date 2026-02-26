package pl.nowakowski.domain.exception;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ProcessingExceptionTest {

    @Test
    void shouldCreateProcessingExceptionWithMessage() {
        // Given
        String message = "Processing failed";

        // When
        ProcessingException exception = new ProcessingException(message);

        // Then
        assertNotNull(exception);
        assertEquals(message, exception.getMessage());
    }

    @Test
    void shouldCreateProcessingExceptionWithFormattedMessage() {
        // Given
        String operation = "save";
        String entity = "Customer";

        // When
        ProcessingException exception = new ProcessingException(
                "Failed to %s entity: [%s]".formatted(operation, entity)
        );

        // Then
        assertNotNull(exception);
        assertEquals("Failed to save entity: [Customer]", exception.getMessage());
    }

    @Test
    void shouldBeInstanceOfRuntimeException() {
        // Given
        ProcessingException exception = new ProcessingException("Test message");

        // Then
        assertTrue(exception instanceof RuntimeException);
    }
}
