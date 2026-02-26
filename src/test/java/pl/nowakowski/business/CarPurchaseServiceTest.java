package pl.nowakowski.business;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import pl.nowakowski.domain.*;

import java.math.BigDecimal;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CarPurchaseServiceTest {

    @Mock
    private CarService carService;

    @Mock
    private SalesmanService salesmanService;

    @Mock
    private CustomerService customerService;

    @InjectMocks
    private CarPurchaseService carPurchaseService;

    private CarToBuy testCar;
    private Salesman testSalesman;
    private Customer testCustomer;
    private CarPurchaseRequest testRequestForNewCustomer;
    private CarPurchaseRequest testRequestForExistingCustomer;

    @BeforeEach
    void setUp() {
        testCar = CarToBuy.builder()
                .carToBuyId(1)
                .vin("1FT7X2B60FEA74019")
                .brand("Ford")
                .model("F-150")
                .year(2020)
                .color("Red")
                .price(new BigDecimal("50000.00"))
                .build();

        testSalesman = Salesman.builder()
                .salesmanId(1)
                .name("John")
                .surname("Doe")
                .pesel("12345678901")
                .build();

        testCustomer = Customer.builder()
                .customerId(1)
                .name("Jane")
                .surname("Smith")
                .email("jane@example.com")
                .phone("+48 123 456 789")
                .invoices(Set.of())
                .build();

        testRequestForNewCustomer = CarPurchaseRequest.builder()
                .customerName("Jane")
                .customerSurname("Smith")
                .customerPhone("+48 123 456 789")
                .customerEmail("jane@example.com")
                .customerAddressCountry("Poland")
                .customerAddressCity("Warsaw")
                .customerAddressPostalCode("00-001")
                .customerAddressStreet("Main Street 123")
                .carVin("1FT7X2B60FEA74019")
                .salesmanPesel("12345678901")
                .build();

        testRequestForExistingCustomer = CarPurchaseRequest.builder()
                .existingCustomerEmail("existing@example.com")
                .carVin("1FT7X2B60FEA74019")
                .salesmanPesel("12345678901")
                .build();
    }

    @Test
    void shouldReturnAvailableCars() {
        // Given
        when(carService.findAvailableCars()).thenReturn(List.of(testCar));

        // When
        List<CarToBuy> result = carPurchaseService.availableCars();

        // Then
        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(testCar, result.get(0));
        verify(carService, times(1)).findAvailableCars();
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

        when(carService.searchAvailableCars(brand, model, yearFrom, yearTo, color, priceFrom, priceTo))
                .thenReturn(List.of(testCar));

        // When
        List<CarToBuy> result = carPurchaseService.searchAvailableCars(brand, model, yearFrom, yearTo, color, priceFrom, priceTo);

        // Then
        assertNotNull(result);
        assertEquals(1, result.size());
        verify(carService, times(1)).searchAvailableCars(brand, model, yearFrom, yearTo, color, priceFrom, priceTo);
    }

    @Test
    void shouldReturnAvailableSalesmen() {
        // Given
        when(salesmanService.findAvailable()).thenReturn(List.of(testSalesman));

        // When
        List<Salesman> result = carPurchaseService.availableSalesmen();

        // Then
        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(testSalesman, result.get(0));
        verify(salesmanService, times(1)).findAvailable();
    }

    @Test
    void shouldPurchaseForNewCustomer() {
        // Given
        when(carService.findCarToBuy("1FT7X2B60FEA74019")).thenReturn(testCar);
        when(salesmanService.findSalesman("12345678901")).thenReturn(testSalesman);
        doNothing().when(customerService).issueInvoice(any(Customer.class));

        // When
        Invoice result = carPurchaseService.purchase(testRequestForNewCustomer);

        // Then
        assertNotNull(result);
        assertNotNull(result.getInvoiceNumber());
        assertNotNull(result.getDateTime());
        assertEquals(testCar, result.getCar());
        assertEquals(testSalesman, result.getSalesman());
        verify(carService, times(1)).findCarToBuy("1FT7X2B60FEA74019");
        verify(salesmanService, times(1)).findSalesman("12345678901");
        verify(customerService, times(1)).issueInvoice(any(Customer.class));
    }


    @Test
    void shouldBuildCustomerCorrectly() {
        // Given
        when(carService.findCarToBuy("1FT7X2B60FEA74019")).thenReturn(testCar);
        when(salesmanService.findSalesman("12345678901")).thenReturn(testSalesman);
        doNothing().when(customerService).issueInvoice(any(Customer.class));

        // When
        carPurchaseService.purchase(testRequestForNewCustomer);

        // Then - verify that customerService.issueInvoice was called with correct customer data
        verify(customerService).issueInvoice(argThat(customer ->
                customer.getName().equals("Jane") &&
                        customer.getSurname().equals("Smith") &&
                        customer.getPhone().equals("+48 123 456 789") &&
                        customer.getEmail().equals("jane@example.com") &&
                        customer.getAddress() != null &&
                        customer.getAddress().getCountry().equals("Poland") &&
                        customer.getAddress().getCity().equals("Warsaw") &&
                        customer.getAddress().getPostalCode().equals("00-001") &&
                        customer.getAddress().getAddress().equals("Main Street 123")
        ));
    }

    @Test
    void shouldBuildInvoiceCorrectly() {
        // Given
        when(carService.findCarToBuy("1FT7X2B60FEA74019")).thenReturn(testCar);
        when(salesmanService.findSalesman("12345678901")).thenReturn(testSalesman);
        doNothing().when(customerService).issueInvoice(any(Customer.class));

        // When
        Invoice result = carPurchaseService.purchase(testRequestForNewCustomer);

        // Then
        assertNotNull(result.getInvoiceNumber());
        assertFalse(result.getInvoiceNumber().isEmpty());
        assertNotNull(result.getDateTime());
        assertEquals(testCar, result.getCar());
        assertEquals(testSalesman, result.getSalesman());
    }
}
