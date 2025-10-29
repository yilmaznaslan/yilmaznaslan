package com.yilmaznaslan.springdemo.vehicle;

import java.math.BigDecimal;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/vehicles")
@Tag(name = "Vehicle Management", description = "APIs for managing vehicles in the marketplace")
public class VehiclesController {

    private final VehicleMongoService vehicleService;
    private final VehicleMapper vehicleMapper;

    public VehiclesController(VehicleMongoService vehicleService, VehicleMapper vehicleMapper) {
        this.vehicleService = vehicleService;
        this.vehicleMapper = vehicleMapper;
    }

    @Operation(summary = "Search vehicles", description = "Search vehicles with advanced filtering and pagination")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved vehicles"),
            @ApiResponse(responseCode = "400", description = "Invalid search parameters")
    })
    @PostMapping("/search")
    public ResponseEntity<VehicleSearchResponse> searchVehicles(@Valid @RequestBody VehicleSearchRequest searchRequest) {
        final VehicleSearchResponse response = vehicleService.searchVehicles(searchRequest);
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "Get all vehicles", description = "Retrieve all vehicles with pagination")
    @GetMapping
    public ResponseEntity<Page<VehicleDto>> getAllVehicles(Pageable pageable) {
        final Page<VehicleDto> vehicles = vehicleService.findByDealerId(null, pageable);
        return ResponseEntity.ok(vehicles);
    }

    @Operation(summary = "Get vehicle by ID", description = "Retrieve a specific vehicle by its ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved vehicle"),
            @ApiResponse(responseCode = "404", description = "Vehicle not found")
    })
    @GetMapping("/{id}")
    public ResponseEntity<VehicleDto> getVehicleById(@PathVariable String id) {
        final Optional<VehicleDto> vehicle = vehicleService.findById(id);
        return vehicle.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @Operation(summary = "Create vehicle", description = "Create a new vehicle listing")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Vehicle created successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid vehicle data")
    })
    @PostMapping
    public ResponseEntity<VehicleDto> createVehicle(@Valid @RequestBody CreateVehicleDto createVehicleDto) {
        final VehicleDto createdVehicle = vehicleService.createVehicle(createVehicleDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdVehicle);
    }

    @Operation(summary = "Update vehicle", description = "Update an existing vehicle listing")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Vehicle updated successfully"),
            @ApiResponse(responseCode = "404", description = "Vehicle not found"),
            @ApiResponse(responseCode = "400", description = "Invalid vehicle data")
    })
    @PutMapping("/{id}")
    public ResponseEntity<VehicleDto> updateVehicle(@PathVariable String id, @Valid @RequestBody CreateVehicleDto updateVehicleDto) {
        final Optional<VehicleDto> updatedVehicle = vehicleService.updateVehicle(id, updateVehicleDto);
        return updatedVehicle.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @Operation(summary = "Delete vehicle", description = "Delete a vehicle listing")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Vehicle deleted successfully"),
            @ApiResponse(responseCode = "404", description = "Vehicle not found")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteVehicle(@PathVariable String id) {
        final boolean deleted = vehicleService.deleteVehicle(id);
        return deleted ? ResponseEntity.noContent().build() : ResponseEntity.notFound().build();
    }

    @Operation(summary = "Get vehicles by dealer", description = "Retrieve all vehicles for a specific dealer")
    @GetMapping("/dealer/{dealerId}")
    public ResponseEntity<Page<VehicleDto>> getVehiclesByDealer(@PathVariable Long dealerId, Pageable pageable) {
        final Page<VehicleDto> vehicles = vehicleService.findByDealerId(dealerId, pageable);
        return ResponseEntity.ok(vehicles);
    }

    @Operation(summary = "Get vehicles by brand", description = "Retrieve vehicles filtered by brand")
    @GetMapping("/brand/{brand}")
    public ResponseEntity<Page<VehicleDto>> getVehiclesByBrand(@PathVariable String brand, Pageable pageable) {
        final Page<VehicleDto> vehicles = vehicleService.findByBrand(brand, pageable);
        return ResponseEntity.ok(vehicles);
    }

    @Operation(summary = "Get vehicles by price range", description = "Retrieve vehicles within a specific price range")
    @GetMapping("/price-range")
    public ResponseEntity<Page<VehicleDto>> getVehiclesByPriceRange(
            @Parameter(description = "Minimum price") @RequestParam BigDecimal minPrice,
            @Parameter(description = "Maximum price") @RequestParam BigDecimal maxPrice,
            Pageable pageable) {
        final Page<VehicleDto> vehicles = vehicleService.findByPriceRange(minPrice, maxPrice, pageable);
        return ResponseEntity.ok(vehicles);
    }

    @Operation(summary = "Get vehicles by year range", description = "Retrieve vehicles within a specific year range")
    @GetMapping("/year-range")
    public ResponseEntity<Page<VehicleDto>> getVehiclesByYearRange(
            @Parameter(description = "Minimum year") @RequestParam Integer minYear,
            @Parameter(description = "Maximum year") @RequestParam Integer maxYear,
            Pageable pageable) {
        final Page<VehicleDto> vehicles = vehicleService.findByYearRange(minYear, maxYear, pageable);
        return ResponseEntity.ok(vehicles);
    }

    @Operation(summary = "Get vehicles by mileage range", description = "Retrieve vehicles within a specific mileage range")
    @GetMapping("/mileage-range")
    public ResponseEntity<Page<VehicleDto>> getVehiclesByMileageRange(
            @Parameter(description = "Minimum mileage") @RequestParam Long minMileage,
            @Parameter(description = "Maximum mileage") @RequestParam Long maxMileage,
            Pageable pageable) {
        final Page<VehicleDto> vehicles = vehicleService.findByMileageRange(minMileage, maxMileage, pageable);
        return ResponseEntity.ok(vehicles);
    }

    @Operation(summary = "Get vehicles by location", description = "Retrieve vehicles in a specific location")
    @GetMapping("/location")
    public ResponseEntity<Page<VehicleDto>> getVehiclesByLocation(
            @Parameter(description = "City") @RequestParam String city,
            @Parameter(description = "State") @RequestParam String state,
            @Parameter(description = "Country") @RequestParam String country,
            Pageable pageable) {
        final Page<VehicleDto> vehicles = vehicleService.findByLocation(city, state, country, pageable);
        return ResponseEntity.ok(vehicles);
    }

    @Operation(summary = "Search vehicles by text", description = "Search vehicles using full-text search")
    @GetMapping("/search-text")
    public ResponseEntity<Page<VehicleDto>> searchVehiclesByText(
            @Parameter(description = "Search text") @RequestParam String searchText,
            Pageable pageable) {
        final Page<VehicleDto> vehicles = vehicleService.findByTextSearch(searchText, pageable);
        return ResponseEntity.ok(vehicles);
    }

    @Operation(summary = "Get vehicle statistics", description = "Get statistics about vehicles in the marketplace")
    @GetMapping("/stats")
    public ResponseEntity<VehicleStatsDto> getVehicleStats() {
        return ResponseEntity.ok(new VehicleStatsDto());
    }

    public static class VehicleStatsDto {
        private long totalVehicles;
        private long activeVehicles;
        private long soldVehicles;
        private BigDecimal averagePrice;
        private String mostPopularBrand;

        public VehicleStatsDto() {}

        public long getTotalVehicles() { return totalVehicles; }
        public void setTotalVehicles(long totalVehicles) { this.totalVehicles = totalVehicles; }

        public long getActiveVehicles() { return activeVehicles; }
        public void setActiveVehicles(long activeVehicles) { this.activeVehicles = activeVehicles; }

        public long getSoldVehicles() { return soldVehicles; }
        public void setSoldVehicles(long soldVehicles) { this.soldVehicles = soldVehicles; }

        public BigDecimal getAveragePrice() { return averagePrice; }
        public void setAveragePrice(BigDecimal averagePrice) { this.averagePrice = averagePrice; }

        public String getMostPopularBrand() { return mostPopularBrand; }
        public void setMostPopularBrand(String mostPopularBrand) { this.mostPopularBrand = mostPopularBrand; }
    }
}