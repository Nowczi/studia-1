package pl.nowakowski.business;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import pl.nowakowski.business.dao.CarServiceRequestDAO;
import pl.nowakowski.domain.*;
import pl.nowakowski.domain.exception.NotFoundException;
import pl.nowakowski.domain.exception.ProcessingException;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CarServiceRequestServiceTest {

    @Mock
    private MechanicService mechanicService;

    @Mock
    private CarService carService;

    @Mock
    private CustomerService customerService;

    @Mock
    private CarServiceRequestDAO carServiceRequestDAO;

    @InjectMocks
    private CarServiceRequestService carServiceRequestService;

    private Mechanic testMechanic;
    private CarToService testCarToService;
    private Customer testCustomer;
    private CarServiceRequest testServiceRequest;

    @BeforeEach
    void setUp() {
        testMechanic = Mechanic.builder()
                .mechanicId(1)
                .name("John")
                .surname("Doe")
                .pesel("12345678901")
                .build();

        testCarToService = CarToService.builder()
                .carToServiceId(1)
                .vin("1FT7X2B60FEA74019")
                .brand("Ford")
                .model("F-150")
                .year(2020)
                .build();

        testCustomer = Customer.builder()
                .customerId(1)
                .name("Jane")
                .surname("Smith")
                .email("jane@example.com")
                .invoices(Set.of())
                .carServiceRequests(Set.of())
                .build();

        testServiceRequest = CarServiceRequest.builder()
                .carServiceRequestId(1)
                .carServiceRequestNumber("2024.1.1-10.30.0.50")
                .customer(testCustomer)
                .car(testCarToService)
                .customerComment("Oil change needed")
                .build();
    }

    @Test
    void shouldReturnAvailableMechanics() {
        // Given
        when(mechanicService.findAvailable()).thenReturn(List.of(testMechanic));

        // When
        List<Mechanic> result = carServiceRequestService.availableMechanics();

        // Then
        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(testMechanic, result.get(0));
        verify(mechanicService, times(1)).findAvailable();
    }

    @Test
    void shouldReturnAvailableServiceRequests() {
        // Given
        when(carServiceRequestDAO.findAvailable()).thenReturn(List.of(testServiceRequest));

        // When
        List<CarServiceRequest> result = carServiceRequestService.availableServiceRequests();

        // Then
        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(testServiceRequest, result.get(0));
        verify(carServiceRequestDAO, times(1)).findAvailable();
    }

    @Test
    void shouldFindServiceRequestByNumberWithDetails() {
        // Given
        when(carServiceRequestDAO.findByServiceRequestNumberWithDetails("2024.1.1-10.30.0.50"))
                .thenReturn(Optional.of(testServiceRequest));

        // When
        Optional<CarServiceRequest> result = carServiceRequestService.findServiceRequestByNumberWithDetails("2024.1.1-10.30.0.50");

        // Then
        assertTrue(result.isPresent());
        assertEquals(testServiceRequest, result.get());
        verify(carServiceRequestDAO, times(1)).findByServiceRequestNumberWithDetails("2024.1.1-10.30.0.50");
    }

    @Test
    void shouldFindAnyActiveServiceRequest() {
        // Given
        when(carServiceRequestDAO.findActiveServiceRequestsByCarVin("1FT7X2B60FEA74019"))
                .thenReturn(Set.of(testServiceRequest));

        // When
        CarServiceRequest result = carServiceRequestService.findAnyActiveServiceRequest("1FT7X2B60FEA74019");

        // Then
        assertNotNull(result);
        assertEquals(testServiceRequest, result);
        verify(carServiceRequestDAO, times(1)).findActiveServiceRequestsByCarVin("1FT7X2B60FEA74019");
    }

    @Test
    void shouldThrowProcessingExceptionWhenMultipleActiveRequests() {
        // Given
        CarServiceRequest secondRequest = CarServiceRequest.builder()
                .carServiceRequestId(2)
                .carServiceRequestNumber("2024.1.2-11.00.0.60")
                .build();
        when(carServiceRequestDAO.findActiveServiceRequestsByCarVin("1FT7X2B60FEA74019"))
                .thenReturn(Set.of(testServiceRequest, secondRequest));

        // When & Then
        ProcessingException exception = assertThrows(ProcessingException.class, () -> {
            carServiceRequestService.findAnyActiveServiceRequest("1FT7X2B60FEA74019");
        });
        assertTrue(exception.getMessage().contains("There should be only one active service request"));
    }

}
