//package pl.nowakowski.domain;
//
//import org.junit.jupiter.api.Test;
//
//import java.math.BigDecimal;
//import java.time.OffsetDateTime;
//import java.time.ZoneId;
//import java.util.List;
//
//import static org.junit.jupiter.api.Assertions.*;
//
//class CarHistoryTest {
//
//    @Test
//    void shouldBuildCarHistorySuccessfully() {
//        // Given
//        CarHistory.ServiceWork serviceWork = CarHistory.ServiceWork.builder()
//                .serviceCode("SRV-001")
//                .description("Oil change")
//                .price(new BigDecimal("100.00"))
//                .mechanicName("John")
//                .mechanicSurname("Doe")
//                .hours(2)
//                .mechanicComment("Work completed")
//                .build();
//
//        CarHistory.Part part = CarHistory.Part.builder()
//                .serialNumber("PART-001")
//                .description("Oil filter")
//                .price(new BigDecimal("25.00"))
//                .quantity(1)
//                .build();
//
//        OffsetDateTime receivedDateTime = OffsetDateTime.now(ZoneId.of("Europe/Warsaw"));
//        OffsetDateTime completedDateTime = receivedDateTime.plusHours(2);
//
//        CarHistory.CarServiceRequest serviceRequest = CarHistory.CarServiceRequest.builder()
//                .carServiceRequestNumber("2024.1.1-10.30.0.50")
//                .receivedDateTime(receivedDateTime)
//                .completedDateTime(completedDateTime)
//                .customerComment("Oil change needed")
//                .serviceWorks(List.of(serviceWork))
//                .parts(List.of(part))
//                .build();
//
//        // When
//        CarHistory carHistory = CarHistory.builder()
//                .carVin("1FT7X2B60FEA74019")
//                .carServiceRequests(List.of(serviceRequest))
//                .build();
//
//        // Then
//        assertNotNull(carHistory);
//        assertEquals("1FT7X2B60FEA74019", carHistory.getCarVin());
//        assertNotNull(carHistory.getCarServiceRequests());
//        assertEquals(1, carHistory.getCarServiceRequests().size());
//    }
//
//    @Test
//    void shouldBuildServiceWorkSuccessfully() {
//        // Given & When
//        CarHistory.ServiceWork serviceWork = CarHistory.ServiceWork.builder()
//                .serviceCode("SRV-001")
//                .description("Oil change")
//                .price(new BigDecimal("100.00"))
//                .mechanicName("John")
//                .mechanicSurname("Doe")
//                .hours(2)
//                .mechanicComment("Work completed")
//                .build();
//
//        // Then
//        assertNotNull(serviceWork);
//        assertEquals("SRV-001", serviceWork.getServiceCode());
//        assertEquals("Oil change", serviceWork.getDescription());
//        assertEquals(new BigDecimal("100.00"), serviceWork.getPrice());
//        assertEquals("John", serviceWork.getMechanicName());
//        assertEquals("Doe", serviceWork.getMechanicSurname());
//        assertEquals(2, serviceWork.getHours());
//        assertEquals("Work completed", serviceWork.getMechanicComment());
//    }
//
//    @Test
//    void shouldBuildCarServiceRequestSuccessfully() {
//        // Given
//        OffsetDateTime receivedDateTime = OffsetDateTime.now(ZoneId.of("Europe/Warsaw"));
//        OffsetDateTime completedDateTime = receivedDateTime.plusHours(2);
//
//        // When
//        CarHistory.CarServiceRequest serviceRequest = CarHistory.CarServiceRequest.builder()
//                .carServiceRequestNumber("2024.1.1-10.30.0.50")
//                .receivedDateTime(receivedDateTime)
//                .completedDateTime(completedDateTime)
//                .customerComment("Oil change needed")
//                .serviceWorks(List.of())
//                .parts(List.of())
//                .build();
//
//        // Then
//        assertNotNull(serviceRequest);
//        assertEquals("2024.1.1-10.30.0.50", serviceRequest.getCarServiceRequestNumber());
//        assertEquals(receivedDateTime, serviceRequest.getReceivedDateTime());
//        assertEquals(completedDateTime, serviceRequest.getCompletedDateTime());
//        assertEquals("Oil change needed", serviceRequest.getCustomerComment());
//    }
//
//    @Test
//    void shouldTestToString() {
//        // Given
//        CarHistory carHistory = CarHistory.builder()
//                .carVin("1FT7X2B60FEA74019")
//                .carServiceRequests(List.of())
//                .build();
//
//        // When
//        String toString = carHistory.toString();
//
//        // Then
//        assertTrue(toString.contains("1FT7X2B60FEA74019"));
//    }
//}
