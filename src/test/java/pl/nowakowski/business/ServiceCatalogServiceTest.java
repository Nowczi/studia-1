package pl.nowakowski.business;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import pl.nowakowski.business.dao.ServiceDAO;
import pl.nowakowski.domain.Service;
import pl.nowakowski.domain.exception.NotFoundException;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ServiceCatalogServiceTest {

    @Mock
    private ServiceDAO serviceDAO;

    @InjectMocks
    private ServiceCatalogService serviceCatalogService;

    private Service testService;

    @BeforeEach
    void setUp() {
        testService = Service.builder()
                .serviceId(1)
                .serviceCode("SRV-001")
                .description("Oil change")
                .price(new BigDecimal("100.00"))
                .build();
    }

    @Test
    void shouldFindServiceByServiceCode() {
        // Given
        when(serviceDAO.findByServiceCode("SRV-001")).thenReturn(Optional.of(testService));

        // When
        Service result = serviceCatalogService.findService("SRV-001");

        // Then
        assertNotNull(result);
        assertEquals(testService, result);
        verify(serviceDAO, times(1)).findByServiceCode("SRV-001");
    }

    @Test
    void shouldThrowNotFoundExceptionWhenServiceNotFound() {
        // Given
        when(serviceDAO.findByServiceCode("INVALID-SRV")).thenReturn(Optional.empty());

        // When & Then
        NotFoundException exception = assertThrows(NotFoundException.class, () -> {
            serviceCatalogService.findService("INVALID-SRV");
        });
        assertTrue(exception.getMessage().contains("Could not find service by service code"));
        verify(serviceDAO, times(1)).findByServiceCode("INVALID-SRV");
    }

    @Test
    void shouldFindAllServices() {
        // Given
        when(serviceDAO.findAll()).thenReturn(List.of(testService));

        // When
        List<Service> result = serviceCatalogService.findAll();

        // Then
        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(testService, result.get(0));
        verify(serviceDAO, times(1)).findAll();
    }
}
