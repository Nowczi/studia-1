package pl.nowakowski.business;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import pl.nowakowski.business.dao.ServiceRequestProcessingDAO;
import pl.nowakowski.domain.*;

import java.math.BigDecimal;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CarServiceProcessingServiceTest {

    @Mock
    private MechanicService mechanicService;

    @Mock
    private CarService carService;

    @Mock
    private ServiceCatalogService serviceCatalogService;

    @Mock
    private PartCatalogService partCatalogService;

    @Mock
    private CarServiceRequestService carServiceRequestService;

    @Mock
    private ServiceRequestProcessingDAO serviceRequestProcessingDAO;

    @InjectMocks
    private CarServiceProcessingService carServiceProcessingService;

    private Mechanic testMechanic;
    private CarToService testCarToService;
    private Service testService;
    private Part testPart;
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

        testService = Service.builder()
                .serviceId(1)
                .serviceCode("SRV-001")
                .description("Oil change")
                .price(new BigDecimal("100.00"))
                .build();

        testPart = Part.builder()
                .partId(1)
                .serialNumber("PART-001")
                .description("Oil filter")
                .price(new BigDecimal("25.00"))
                .build();

        testServiceRequest = CarServiceRequest.builder()
                .carServiceRequestId(1)
                .carServiceRequestNumber("2024.1.1-10.30.0.50")
                .customerComment("Oil change needed")
                .build();
    }

    @Test
    void shouldProcessServiceRequestWithoutPart() {
        // Given
        CarServiceProcessingRequest request = CarServiceProcessingRequest.builder()
                .mechanicPesel("12345678901")
                .carVin("1FT7X2B60FEA74019")
                .serviceCode("SRV-001")
                .hours(2)
                .comment("Work completed")
                .done(false)
                .build();

        when(mechanicService.findMechanic("12345678901")).thenReturn(testMechanic);
        when(carService.findCarToService("1FT7X2B60FEA74019")).thenReturn(Optional.of(testCarToService));
        when(carServiceRequestService.findAnyActiveServiceRequest("1FT7X2B60FEA74019")).thenReturn(testServiceRequest);
        when(serviceCatalogService.findService("SRV-001")).thenReturn(testService);
        doNothing().when(serviceRequestProcessingDAO).process(any(CarServiceRequest.class), any(ServiceMechanic.class));

        // When
        carServiceProcessingService.process(request);

        // Then
        verify(mechanicService, times(1)).findMechanic("12345678901");
        verify(carService, times(1)).findCarToService("1FT7X2B60FEA74019");
        verify(carServiceRequestService, times(1)).findAnyActiveServiceRequest("1FT7X2B60FEA74019");
        verify(serviceCatalogService, times(1)).findService("SRV-001");
        verify(serviceRequestProcessingDAO, times(1)).process(any(CarServiceRequest.class), any(ServiceMechanic.class));
        verify(serviceRequestProcessingDAO, never()).process(any(CarServiceRequest.class), any(ServiceMechanic.class), any(ServicePart.class));
    }

    @Test
    void shouldProcessServiceRequestWithPart() {
        // Given
        CarServiceProcessingRequest request = CarServiceProcessingRequest.builder()
                .mechanicPesel("12345678901")
                .carVin("1FT7X2B60FEA74019")
                .partSerialNumber("PART-001")
                .partQuantity(2)
                .serviceCode("SRV-001")
                .hours(2)
                .comment("Work completed")
                .done(true)
                .build();

        when(mechanicService.findMechanic("12345678901")).thenReturn(testMechanic);
        when(carService.findCarToService("1FT7X2B60FEA74019")).thenReturn(Optional.of(testCarToService));
        when(carServiceRequestService.findAnyActiveServiceRequest("1FT7X2B60FEA74019")).thenReturn(testServiceRequest);
        when(serviceCatalogService.findService("SRV-001")).thenReturn(testService);
        when(partCatalogService.findPart("PART-001")).thenReturn(testPart);
        doNothing().when(serviceRequestProcessingDAO).process(any(CarServiceRequest.class), any(ServiceMechanic.class), any(ServicePart.class));

        // When
        carServiceProcessingService.process(request);

        // Then
        verify(mechanicService, times(1)).findMechanic("12345678901");
        verify(carService, times(1)).findCarToService("1FT7X2B60FEA74019");
        verify(carServiceRequestService, times(1)).findAnyActiveServiceRequest("1FT7X2B60FEA74019");
        verify(serviceCatalogService, times(1)).findService("SRV-001");
        verify(partCatalogService, times(1)).findPart("PART-001");
        verify(serviceRequestProcessingDAO, times(1)).process(any(CarServiceRequest.class), any(ServiceMechanic.class), any(ServicePart.class));
    }

    @Test
    void shouldCompleteServiceRequest() {
        // Given
        when(carServiceRequestService.findAnyActiveServiceRequest("1FT7X2B60FEA74019")).thenReturn(testServiceRequest);
        doNothing().when(serviceRequestProcessingDAO).markServiceRequestAsCompleted(any(CarServiceRequest.class));

        // When
        carServiceProcessingService.completeServiceRequest("1FT7X2B60FEA74019");

        // Then
        verify(carServiceRequestService, times(1)).findAnyActiveServiceRequest("1FT7X2B60FEA74019");
        verify(serviceRequestProcessingDAO, times(1)).markServiceRequestAsCompleted(any(CarServiceRequest.class));
    }
}
