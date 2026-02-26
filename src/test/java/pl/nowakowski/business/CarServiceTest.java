package pl.nowakowski.business;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import pl.nowakowski.business.dao.CarToBuyDAO;
import pl.nowakowski.business.dao.CarToServiceDAO;
import pl.nowakowski.domain.CarHistory;
import pl.nowakowski.domain.CarToBuy;
import pl.nowakowski.domain.CarToService;
import pl.nowakowski.domain.exception.NotFoundException;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CarServiceTest {

    @Mock
    private CarToBuyDAO carToBuyDAO;

    @Mock
    private CarToServiceDAO carToServiceDAO;

    @InjectMocks
    private CarService carService;

    private CarToBuy testCarToBuy;
    private CarToService testCarToService;

    @BeforeEach
    void setUp() {
        testCarToBuy = CarToBuy.builder()
                .carToBuyId(1)
                .vin("1FT7X2B60FEA74019")
                .brand("Ford")
                .model("F-150")
                .year(2020)
                .color("Red")
                .price(new BigDecimal("50000.00"))
                .build();

        testCarToService = CarToService.builder()
                .carToServiceId(1)
                .vin("1FT7X2B60FEA74019")
                .brand("Ford")
                .model("F-150")
                .year(2020)
                .build();
    }

    @Test
    void shouldFindAvailableCars() {
        // Given
        when(carToBuyDAO.findAvailable()).thenReturn(List.of(testCarToBuy));

        // When
        List<CarToBuy> result = carService.findAvailableCars();

        // Then
        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(testCarToBuy, result.get(0));
        verify(carToBuyDAO, times(1)).findAvailable();
    }

    @Test
    void shouldSearchAvailableCars() {
        // Given
        String brand = "Ford";
        String model = "F-150";
        Integer yearFrom = 2019;
        Integer yearTo = 2021;
        String color = "Red";
        BigDecimal priceFrom = new BigDecimal("40000");
        BigDecimal priceTo = new BigDecimal("60000");

        when(carToBuyDAO.searchAvailableCars(brand, model, yearFrom, yearTo, color, priceFrom, priceTo))
                .thenReturn(List.of(testCarToBuy));

        // When
        List<CarToBuy> result = carService.searchAvailableCars(brand, model, yearFrom, yearTo, color, priceFrom, priceTo);

        // Then
        assertNotNull(result);
        assertEquals(1, result.size());
        verify(carToBuyDAO, times(1)).searchAvailableCars(brand, model, yearFrom, yearTo, color, priceFrom, priceTo);
    }

    @Test
    void shouldFindCarToBuyByVin() {
        // Given
        when(carToBuyDAO.findCarToBuyByVin("1FT7X2B60FEA74019")).thenReturn(Optional.of(testCarToBuy));

        // When
        CarToBuy result = carService.findCarToBuy("1FT7X2B60FEA74019");

        // Then
        assertNotNull(result);
        assertEquals(testCarToBuy, result);
        verify(carToBuyDAO, times(1)).findCarToBuyByVin("1FT7X2B60FEA74019");
    }

    @Test
    void shouldThrowNotFoundExceptionWhenCarNotFound() {
        // Given
        when(carToBuyDAO.findCarToBuyByVin("INVALIDVIN1234567")).thenReturn(Optional.empty());

        // When & Then
        NotFoundException exception = assertThrows(NotFoundException.class, () -> {
            carService.findCarToBuy("INVALIDVIN1234567");
        });
        assertTrue(exception.getMessage().contains("Could not find car by vin"));
        verify(carToBuyDAO, times(1)).findCarToBuyByVin("INVALIDVIN1234567");
    }

    @Test
    void shouldFindOptionalCarToBuy() {
        // Given
        when(carToBuyDAO.findCarToBuyByVin("1FT7X2B60FEA74019")).thenReturn(Optional.of(testCarToBuy));

        // When
        Optional<CarToBuy> result = carService.findOptionalCarToBuy("1FT7X2B60FEA74019");

        // Then
        assertTrue(result.isPresent());
        assertEquals(testCarToBuy, result.get());
    }

    @Test
    void shouldSaveCarToBuy() {
        // Given
        when(carToBuyDAO.saveCarToBuy(testCarToBuy)).thenReturn(testCarToBuy);

        // When
        CarToBuy result = carService.saveCarToBuy(testCarToBuy);

        // Then
        assertNotNull(result);
        assertEquals(testCarToBuy, result);
        verify(carToBuyDAO, times(1)).saveCarToBuy(testCarToBuy);
    }

    @Test
    void shouldDeleteCarToBuy() {
        // Given
        doNothing().when(carToBuyDAO).deleteCarToBuy("1FT7X2B60FEA74019");

        // When
        carService.deleteCarToBuy("1FT7X2B60FEA74019");

        // Then
        verify(carToBuyDAO, times(1)).deleteCarToBuy("1FT7X2B60FEA74019");
    }

    @Test
    void shouldFindCarToService() {
        // Given
        when(carToServiceDAO.findCarToServiceByVin("1FT7X2B60FEA74019")).thenReturn(Optional.of(testCarToService));

        // When
        Optional<CarToService> result = carService.findCarToService("1FT7X2B60FEA74019");

        // Then
        assertTrue(result.isPresent());
        assertEquals(testCarToService, result.get());
        verify(carToServiceDAO, times(1)).findCarToServiceByVin("1FT7X2B60FEA74019");
    }

    @Test
    void shouldSaveCarToServiceFromCarToBuy() {
        // Given
        when(carToServiceDAO.saveCarToService(any(CarToService.class))).thenReturn(testCarToService);

        // When
        CarToService result = carService.saveCarToService(testCarToBuy);

        // Then
        assertNotNull(result);
        assertEquals(testCarToBuy.getVin(), result.getVin());
        assertEquals(testCarToBuy.getBrand(), result.getBrand());
        assertEquals(testCarToBuy.getModel(), result.getModel());
        assertEquals(testCarToBuy.getYear(), result.getYear());
        verify(carToServiceDAO, times(1)).saveCarToService(any(CarToService.class));
    }

    @Test
    void shouldSaveCarToService() {
        // Given
        when(carToServiceDAO.saveCarToService(testCarToService)).thenReturn(testCarToService);

        // When
        CarToService result = carService.saveCarToService(testCarToService);

        // Then
        assertNotNull(result);
        assertEquals(testCarToService, result);
        verify(carToServiceDAO, times(1)).saveCarToService(testCarToService);
    }

    @Test
    void shouldFindAllCarsWithHistory() {
        // Given
        when(carToServiceDAO.findAll()).thenReturn(List.of(testCarToService));

        // When
        List<CarToService> result = carService.findAllCarsWithHistory();

        // Then
        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(testCarToService, result.get(0));
        verify(carToServiceDAO, times(1)).findAll();
    }

    @Test
    void shouldFindCarHistoryByVin() {
        // Given
        CarHistory carHistory = CarHistory.builder()
                .carVin("1FT7X2B60FEA74019")
                .carServiceRequests(List.of())
                .build();
        when(carToServiceDAO.findCarHistoryByVin("1FT7X2B60FEA74019")).thenReturn(carHistory);

        // When
        CarHistory result = carService.findCarHistoryByVin("1FT7X2B60FEA74019");

        // Then
        assertNotNull(result);
        assertEquals("1FT7X2B60FEA74019", result.getCarVin());
        verify(carToServiceDAO, times(1)).findCarHistoryByVin("1FT7X2B60FEA74019");
    }
}
