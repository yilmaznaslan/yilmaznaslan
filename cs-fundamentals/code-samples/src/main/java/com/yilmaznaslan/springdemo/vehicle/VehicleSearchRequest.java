package com.yilmaznaslan.springdemo.vehicle;

import java.math.BigDecimal;
import java.util.List;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;

public class VehicleSearchRequest {

    private String query;

    private String brand;

    private String model;

    @Min(value = 1900, message = "Min year must be at least 1900")
    @Max(value = 2025, message = "Max year cannot be in the future")
    private Integer minYear;

    @Min(value = 1900, message = "Min year must be at least 1900")
    @Max(value = 2025, message = "Max year cannot be in the future")
    private Integer maxYear;

    @DecimalMin(value = "0.0", inclusive = true, message = "Min price must be non-negative")
    private BigDecimal minPrice;

    @DecimalMin(value = "0.0", inclusive = true, message = "Max price must be non-negative")
    private BigDecimal maxPrice;

    @Min(value = 0, message = "Min mileage cannot be negative")
    private Long minMileage;

    @Min(value = 0, message = "Max mileage cannot be negative")
    private Long maxMileage;

    private List<VehicleEntity.FuelType> fuelTypes;

    private List<VehicleEntity.TransmissionType> transmissions;

    private List<VehicleEntity.BodyType> bodyTypes;

    @Min(value = 1, message = "Min engine power must be at least 1")
    private Integer minEnginePower;

    @Min(value = 1, message = "Max engine power must be at least 1")
    private Integer maxEnginePower;

    @Min(value = 1, message = "Min engine displacement must be at least 1")
    private Integer minEngineDisplacement;

    @Min(value = 1, message = "Max engine displacement must be at least 1")
    private Integer maxEngineDisplacement;

    @Min(value = 2, message = "Min doors must be at least 2")
    @Max(value = 5, message = "Max doors cannot exceed 5")
    private Integer minDoors;

    @Min(value = 2, message = "Min doors must be at least 2")
    @Max(value = 5, message = "Max doors cannot exceed 5")
    private Integer maxDoors;

    @Min(value = 1, message = "Min seats must be at least 1")
    @Max(value = 9, message = "Max seats cannot exceed 9")
    private Integer minSeats;

    @Min(value = 1, message = "Min seats must be at least 1")
    @Max(value = 9, message = "Max seats cannot exceed 9")
    private Integer maxSeats;

    @Min(value = 0, message = "Min previous owners cannot be negative")
    private Integer minPreviousOwners;

    @Min(value = 0, message = "Max previous owners cannot be negative")
    private Integer maxPreviousOwners;

    private List<VehicleEntity.VehicleCondition> conditions;

    private List<VehicleEntity.VehicleStatus> statuses;

    private String city;

    private String state;

    private String country;

    private Integer radius; // in kilometers

    private Double latitude;

    private Double longitude;

    @Min(value = 0, message = "Page number must be non-negative")
    private Integer page = 0;

    @Min(value = 1, message = "Page size must be at least 1")
    @Max(value = 100, message = "Page size cannot exceed 100")
    private Integer size = 20;

    private SortField sortField = SortField.CREATED_AT;

    private SortDirection sortDirection = SortDirection.DESC;

    public enum SortField {
        PRICE, MILEAGE, YEAR, CREATED_AT, UPDATED_AT, BRAND, MODEL
    }

    public enum SortDirection {
        ASC, DESC
    }

    public VehicleSearchRequest() {}

    public String getQuery() { return query; }
    public void setQuery(String query) { this.query = query; }

    public String getBrand() { return brand; }
    public void setBrand(String brand) { this.brand = brand; }

    public String getModel() { return model; }
    public void setModel(String model) { this.model = model; }

    public Integer getMinYear() { return minYear; }
    public void setMinYear(Integer minYear) { this.minYear = minYear; }

    public Integer getMaxYear() { return maxYear; }
    public void setMaxYear(Integer maxYear) { this.maxYear = maxYear; }

    public BigDecimal getMinPrice() { return minPrice; }
    public void setMinPrice(BigDecimal minPrice) { this.minPrice = minPrice; }

    public BigDecimal getMaxPrice() { return maxPrice; }
    public void setMaxPrice(BigDecimal maxPrice) { this.maxPrice = maxPrice; }

    public Long getMinMileage() { return minMileage; }
    public void setMinMileage(Long minMileage) { this.minMileage = minMileage; }

    public Long getMaxMileage() { return maxMileage; }
    public void setMaxMileage(Long maxMileage) { this.maxMileage = maxMileage; }

    public List<VehicleEntity.FuelType> getFuelTypes() { return fuelTypes; }
    public void setFuelTypes(List<VehicleEntity.FuelType> fuelTypes) { this.fuelTypes = fuelTypes; }

    public List<VehicleEntity.TransmissionType> getTransmissions() { return transmissions; }
    public void setTransmissions(List<VehicleEntity.TransmissionType> transmissions) { this.transmissions = transmissions; }

    public List<VehicleEntity.BodyType> getBodyTypes() { return bodyTypes; }
    public void setBodyTypes(List<VehicleEntity.BodyType> bodyTypes) { this.bodyTypes = bodyTypes; }

    public Integer getMinEnginePower() { return minEnginePower; }
    public void setMinEnginePower(Integer minEnginePower) { this.minEnginePower = minEnginePower; }

    public Integer getMaxEnginePower() { return maxEnginePower; }
    public void setMaxEnginePower(Integer maxEnginePower) { this.maxEnginePower = maxEnginePower; }

    public Integer getMinEngineDisplacement() { return minEngineDisplacement; }
    public void setMinEngineDisplacement(Integer minEngineDisplacement) { this.minEngineDisplacement = minEngineDisplacement; }

    public Integer getMaxEngineDisplacement() { return maxEngineDisplacement; }
    public void setMaxEngineDisplacement(Integer maxEngineDisplacement) { this.maxEngineDisplacement = maxEngineDisplacement; }

    public Integer getMinDoors() { return minDoors; }
    public void setMinDoors(Integer minDoors) { this.minDoors = minDoors; }

    public Integer getMaxDoors() { return maxDoors; }
    public void setMaxDoors(Integer maxDoors) { this.maxDoors = maxDoors; }

    public Integer getMinSeats() { return minSeats; }
    public void setMinSeats(Integer minSeats) { this.minSeats = minSeats; }

    public Integer getMaxSeats() { return maxSeats; }
    public void setMaxSeats(Integer maxSeats) { this.maxSeats = maxSeats; }

    public Integer getMinPreviousOwners() { return minPreviousOwners; }
    public void setMinPreviousOwners(Integer minPreviousOwners) { this.minPreviousOwners = minPreviousOwners; }

    public Integer getMaxPreviousOwners() { return maxPreviousOwners; }
    public void setMaxPreviousOwners(Integer maxPreviousOwners) { this.maxPreviousOwners = maxPreviousOwners; }

    public List<VehicleEntity.VehicleCondition> getConditions() { return conditions; }
    public void setConditions(List<VehicleEntity.VehicleCondition> conditions) { this.conditions = conditions; }

    public List<VehicleEntity.VehicleStatus> getStatuses() { return statuses; }
    public void setStatuses(List<VehicleEntity.VehicleStatus> statuses) { this.statuses = statuses; }

    public String getCity() { return city; }
    public void setCity(String city) { this.city = city; }

    public String getState() { return state; }
    public void setState(String state) { this.state = state; }

    public String getCountry() { return country; }
    public void setCountry(String country) { this.country = country; }

    public Integer getRadius() { return radius; }
    public void setRadius(Integer radius) { this.radius = radius; }

    public Double getLatitude() { return latitude; }
    public void setLatitude(Double latitude) { this.latitude = latitude; }

    public Double getLongitude() { return longitude; }
    public void setLongitude(Double longitude) { this.longitude = longitude; }

    public Integer getPage() { return page; }
    public void setPage(Integer page) { this.page = page; }

    public Integer getSize() { return size; }
    public void setSize(Integer size) { this.size = size; }

    public SortField getSortField() { return sortField; }
    public void setSortField(SortField sortField) { this.sortField = sortField; }

    public SortDirection getSortDirection() { return sortDirection; }
    public void setSortDirection(SortDirection sortDirection) { this.sortDirection = sortDirection; }
}
