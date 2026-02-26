package pl.nowakowski.business;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import pl.nowakowski.business.dao.CustomerDAO;
import pl.nowakowski.domain.Customer;
import pl.nowakowski.domain.exception.NotFoundException;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CustomerServiceTest {

    @Mock
    private CustomerDAO customerDAO;

    @InjectMocks
    private CustomerService customerService;

    private Customer testCustomer;

    @BeforeEach
    void setUp() {
        testCustomer = Customer.builder()
                .customerId(1)
                .name("John")
                .surname("Doe")
                .phone("+48 123 456 789")
                .email("john@example.com")
                .build();
    }

    @Test
    void shouldIssueInvoice() {
        // Given
        doNothing().when(customerDAO).issueInvoice(testCustomer);

        // When
        customerService.issueInvoice(testCustomer);

        // Then
        verify(customerDAO, times(1)).issueInvoice(testCustomer);
    }

    @Test
    void shouldFindCustomerByEmail() {
        // Given
        when(customerDAO.findByEmail("john@example.com")).thenReturn(Optional.of(testCustomer));

        // When
        Customer result = customerService.findCustomer("john@example.com");

        // Then
        assertNotNull(result);
        assertEquals(testCustomer, result);
        verify(customerDAO, times(1)).findByEmail("john@example.com");
    }

    @Test
    void shouldThrowNotFoundExceptionWhenCustomerNotFound() {
        // Given
        when(customerDAO.findByEmail("nonexistent@example.com")).thenReturn(Optional.empty());

        // When & Then
        NotFoundException exception = assertThrows(NotFoundException.class, () -> {
            customerService.findCustomer("nonexistent@example.com");
        });
        assertTrue(exception.getMessage().contains("Could not find customer by email"));
        verify(customerDAO, times(1)).findByEmail("nonexistent@example.com");
    }

    @Test
    void shouldSaveServiceRequest() {
        // Given
        doNothing().when(customerDAO).saveServiceRequest(testCustomer);

        // When
        customerService.saveServiceRequest(testCustomer);

        // Then
        verify(customerDAO, times(1)).saveServiceRequest(testCustomer);
    }

    @Test
    void shouldSaveCustomer() {
        // Given
        when(customerDAO.saveCustomer(testCustomer)).thenReturn(testCustomer);

        // When
        Customer result = customerService.saveCustomer(testCustomer);

        // Then
        assertNotNull(result);
        assertEquals(testCustomer, result);
        verify(customerDAO, times(1)).saveCustomer(testCustomer);
    }
}
