package pl.nowakowski.api.controller;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
class HomeControllerTest {

    @InjectMocks
    private HomeController homeController;

    @Test
    void shouldReturnHomePage() {
        // When
        String result = homeController.homePage();

        // Then
        assertEquals("home", result);
    }

    @Test
    void shouldReturnLoginPage() {
        // When
        String result = homeController.loginPage();

        // Then
        assertEquals("login", result);
    }
}
