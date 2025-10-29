package com.yilmaznaslan.springdemo.vehicle;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest
@ActiveProfiles("test")
class VehicleMongoServiceTest {

    @Autowired
    private VehicleMongoService vehicleMongoService;

    @Test
    void testCreateVehicle() {
        final CreateVehicleDto createVehicleDto = new CreateVehicleDto();
        createVehicleDto.setBrand("BMW");
        createVehicleDto.setModel("3 Series");
        createVehicleDto.setYear(2022);
        createVehicleDto.setPrice(new BigDecimal("35000"));
        createVehicleDto.setMileage(15000L);
        createVehicleDto.setFuelType(VehicleEntity.FuelType.GASOLINE);
        createVehicleDto.setTransmission(VehicleEntity.TransmissionType.AUTOMATIC);
        createVehicleDto.setBodyType(VehicleEntity.BodyType.SEDAN);
        createVehicleDto.setEnginePower(184);
        createVehicleDto.setEngineDisplacement(2000);
        createVehicleDto.setNumberOfDoors(4);
        createVehicleDto.setNumberOfSeats(5);
        createVehicleDto.setNumberOfPreviousOwners(1);
        createVehicleDto.setFirstRegistrationDate(LocalDate.of(2022, 1, 15));
        createVehicleDto.setLastServiceDate(LocalDate.of(2023, 12, 1));
        createVehicleDto.setNextInspectionDate(LocalDate.of(2024, 1, 15));
        createVehicleDto.setCondition(VehicleEntity.VehicleCondition.USED);
        createVehicleDto.setImageUrls(List.of("https://example.com/image1.jpg"));
        createVehicleDto.setFeatures(List.of("Navigation", "Bluetooth", "Cruise Control"));
        createVehicleDto.setDescription("Well-maintained BMW 3 Series with low mileage");
        
        final CreateVehicleDto.LocationDto location = new CreateVehicleDto.LocationDto();
        location.setCity("Berlin");
        location.setState("Berlin");
        location.setCountry("Germany");
        location.setPostalCode("10115");
        location.setAddress("Unter den Linden 1");
        createVehicleDto.setLocation(location);
        createVehicleDto.setDealerId(1L);

        final VehicleDto createdVehicle = vehicleMongoService.createVehicle(createVehicleDto);

        assertNotNull(createdVehicle);
        assertEquals("BMW", createdVehicle.getBrand());
        assertEquals("3 Series", createdVehicle.getModel());
        assertEquals(2022, createdVehicle.getYear());
        assertEquals(new BigDecimal("35000"), createdVehicle.getPrice());
        assertEquals(VehicleEntity.VehicleStatus.PENDING_APPROVAL, createdVehicle.getStatus());
    }

    @Test
    void testSearchVehicles() {
        final VehicleSearchRequest searchRequest = new VehicleSearchRequest();
        searchRequest.setBrand("BMW");
        searchRequest.setMinYear(2020);
        searchRequest.setMaxYear(2024);
        searchRequest.setMinPrice(new BigDecimal("25000"));
        searchRequest.setMaxPrice(new BigDecimal("50000"));
        searchRequest.setPage(0);
        searchRequest.setSize(10);

        final VehicleSearchResponse response = vehicleMongoService.searchVehicles(searchRequest);

        assertNotNull(response);
        assertNotNull(response.getVehicles());
        assertTrue(response.getTotalElements() >= 0);
        assertTrue(response.getTotalPages() >= 0);
        assertEquals(0, response.getCurrentPage());
        assertEquals(10, response.getSize());
    }
}
