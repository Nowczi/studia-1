package pl.nowakowski.api.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.validation.BindException;
import org.springframework.validation.FieldError;
import org.springframework.web.servlet.ModelAndView;
import pl.nowakowski.domain.exception.NotFoundException;
import pl.nowakowski.domain.exception.ProcessingException;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class GlobalExceptionHandlerTest {

    @InjectMocks
    private GlobalExceptionHandler globalExceptionHandler;

    @Test
    void shouldHandleNotFoundException() {
        // Given
        NotFoundException exception = new NotFoundException("Resource not found");

        // When
        ModelAndView result = globalExceptionHandler.handleNoResourceFound(exception);

        // Then
        assertNotNull(result);
        assertEquals("error", result.getViewName());
        assertTrue(result.getModel().containsKey("errorMessage"));
        String errorMessage = (String) result.getModel().get("errorMessage");
        assertTrue(errorMessage.contains("Could not find a resource"));
        assertTrue(errorMessage.contains("Resource not found"));
    }

    @Test
    void shouldHandleProcessingException() {
        // Given
        ProcessingException exception = new ProcessingException("Processing failed");

        // When
        ModelAndView result = globalExceptionHandler.handleException(exception);

        // Then
        assertNotNull(result);
        assertEquals("error", result.getViewName());
        assertTrue(result.getModel().containsKey("errorMessage"));
        String errorMessage = (String) result.getModel().get("errorMessage");
        assertTrue(errorMessage.contains("Processing exception occurred"));
        assertTrue(errorMessage.contains("Processing failed"));
    }

    @Test
    void shouldHandleGenericException() {
        // Given
        Exception exception = new Exception("Something went wrong");

        // When
        ModelAndView result = globalExceptionHandler.handleException(exception);

        // Then
        assertNotNull(result);
        assertEquals("error", result.getViewName());
        assertTrue(result.getModel().containsKey("errorMessage"));
        String errorMessage = (String) result.getModel().get("errorMessage");
        assertTrue(errorMessage.contains("Other exception occurred"));
        assertTrue(errorMessage.contains("Something went wrong"));
    }

    @Test
    void shouldHandleBindException() {
        // Given
        BindException bindException = new BindException(new Object(), "testObject");
        bindException.addError(new FieldError("testObject", "fieldName", "rejectedValue", false, null, null, "Error message"));

        // When
        ModelAndView result = globalExceptionHandler.handleException(bindException);

        // Then
        assertNotNull(result);
        assertEquals("error", result.getViewName());
        assertTrue(result.getModel().containsKey("errorMessage"));
        String errorMessage = (String) result.getModel().get("errorMessage");
        assertTrue(errorMessage.contains("Bad request for field"));
    }
}
