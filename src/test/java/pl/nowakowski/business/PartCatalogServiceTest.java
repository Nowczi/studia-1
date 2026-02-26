package pl.nowakowski.business;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import pl.nowakowski.business.dao.PartDAO;
import pl.nowakowski.domain.Part;
import pl.nowakowski.domain.exception.NotFoundException;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PartCatalogServiceTest {

    @Mock
    private PartDAO partDAO;

    @InjectMocks
    private PartCatalogService partCatalogService;

    private Part testPart;

    @BeforeEach
    void setUp() {
        testPart = Part.builder()
                .partId(1)
                .serialNumber("PART-001")
                .description("Brake pads")
                .price(new BigDecimal("150.00"))
                .build();
    }

    @Test
    void shouldFindPartBySerialNumber() {
        // Given
        when(partDAO.findBySerialNumber("PART-001")).thenReturn(Optional.of(testPart));

        // When
        Part result = partCatalogService.findPart("PART-001");

        // Then
        assertNotNull(result);
        assertEquals(testPart, result);
        verify(partDAO, times(1)).findBySerialNumber("PART-001");
    }

    @Test
    void shouldThrowNotFoundExceptionWhenPartNotFound() {
        // Given
        when(partDAO.findBySerialNumber("INVALID-PART")).thenReturn(Optional.empty());

        // When & Then
        NotFoundException exception = assertThrows(NotFoundException.class, () -> {
            partCatalogService.findPart("INVALID-PART");
        });
        assertTrue(exception.getMessage().contains("Could not find part by part serial number"));
        verify(partDAO, times(1)).findBySerialNumber("INVALID-PART");
    }

    @Test
    void shouldFindAllParts() {
        // Given
        when(partDAO.findAll()).thenReturn(List.of(testPart));

        // When
        List<Part> result = partCatalogService.findAll();

        // Then
        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(testPart, result.get(0));
        verify(partDAO, times(1)).findAll();
    }
}
