package pl.nowakowski.domain;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CarServiceProcessingRequestTest {

    @Test
    void shouldBuildCarServiceProcessingRequestWithPart() {
        // Given & When
        CarServiceProcessingRequest request = CarServiceProcessingRequest.builder()
                .mechanicPesel("12345678901")
                .carVin("1FT7X2B60FEA74019")
                .partSerialNumber("PART-001")
                .partQuantity(2)
                .serviceCode("SRV-001")
                .hours(3)
                .comment("Work completed")
                .done(true)
                .build();

        // Then
        assertNotNull(request);
        assertEquals("12345678901", request.getMechanicPesel());
        assertEquals("1FT7X2B60FEA74019", request.getCarVin());
        assertEquals("PART-001", request.getPartSerialNumber());
        assertEquals(2, request.getPartQuantity());
        assertEquals("SRV-001", request.getServiceCode());
        assertEquals(3, request.getHours());
        assertEquals("Work completed", request.getComment());
        assertTrue(request.getDone());
    }

    @Test
    void shouldBuildCarServiceProcessingRequestWithoutPart() {
        // Given & When
        CarServiceProcessingRequest request = CarServiceProcessingRequest.builder()
                .mechanicPesel("12345678901")
                .carVin("1FT7X2B60FEA74019")
                .serviceCode("SRV-001")
                .hours(2)
                .comment("Work completed")
                .done(false)
                .build();

        // Then
        assertNotNull(request);
        assertNull(request.getPartSerialNumber());
        assertNull(request.getPartQuantity());
    }

    @Test
    void shouldReturnTrueWhenPartNotIncluded() {
        // Given
        CarServiceProcessingRequest request = CarServiceProcessingRequest.builder()
                .mechanicPesel("12345678901")
                .carVin("1FT7X2B60FEA74019")
                .serviceCode("SRV-001")
                .hours(2)
                .done(false)
                .build();

        // When & Then
        assertTrue(request.partNotIncluded());
    }

    @Test
    void shouldReturnTrueWhenPartSerialNumberIsNone() {
        // Given
        CarServiceProcessingRequest request = CarServiceProcessingRequest.builder()
                .mechanicPesel("12345678901")
                .carVin("1FT7X2B60FEA74019")
                .partSerialNumber(Part.NONE)
                .partQuantity(2)
                .serviceCode("SRV-001")
                .hours(2)
                .done(false)
                .build();

        // When & Then
        assertTrue(request.partNotIncluded());
    }

    @Test
    void shouldReturnFalseWhenPartIsIncluded() {
        // Given
        CarServiceProcessingRequest request = CarServiceProcessingRequest.builder()
                .mechanicPesel("12345678901")
                .carVin("1FT7X2B60FEA74019")
                .partSerialNumber("PART-001")
                .partQuantity(2)
                .serviceCode("SRV-001")
                .hours(2)
                .done(false)
                .build();

        // When & Then
        assertFalse(request.partNotIncluded());
    }

    @Test
    void shouldTestWithMethod() {
        // Given
        CarServiceProcessingRequest originalRequest = CarServiceProcessingRequest.builder()
                .mechanicPesel("12345678901")
                .carVin("1FT7X2B60FEA74019")
                .serviceCode("SRV-001")
                .hours(2)
                .done(false)
                .build();

        // When
        CarServiceProcessingRequest modifiedRequest = originalRequest.withHours(4);

        // Then
        assertEquals(4, modifiedRequest.getHours());
        assertEquals(originalRequest.getMechanicPesel(), modifiedRequest.getMechanicPesel());
    }
}
