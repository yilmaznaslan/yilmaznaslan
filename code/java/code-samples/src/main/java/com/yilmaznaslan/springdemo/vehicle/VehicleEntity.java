package com.yilmaznaslan.springdemo.vehicle;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import jakarta.persistence.CollectionTable;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Embeddable;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import jakarta.persistence.Table;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

@Entity
@Table(name = "vehicles")
public class VehicleEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Brand is required")
    @Size(max = 50, message = "Brand name must not exceed 50 characters")
    private String brand;

    @NotBlank(message = "Model is required")
    @Size(max = 100, message = "Model name must not exceed 100 characters")
    private String model;

    @NotNull(message = "Year is required")
    @Min(value = 1900, message = "Year must be at least 1900")
    @Max(value = 2025, message = "Year cannot be in the future")
    private Integer year;

    @NotNull(message = "Price is required")
    @DecimalMin(value = "0.0", inclusive = false, message = "Price must be greater than 0")
    @Digits(integer = 10, fraction = 2, message = "Price must have at most 10 integer digits and 2 decimal places")
    private BigDecimal price;

    @NotNull(message = "Mileage is required")
    @Min(value = 0, message = "Mileage cannot be negative")
    private Long mileage;

    @Enumerated(EnumType.STRING)
    @NotNull(message = "Fuel type is required")
    private FuelType fuelType;

    @Enumerated(EnumType.STRING)
    @NotNull(message = "Transmission is required")
    private TransmissionType transmission;

    @Enumerated(EnumType.STRING)
    @NotNull(message = "Body type is required")
    private BodyType bodyType;

    @NotNull(message = "Engine power is required")
    @Min(value = 1, message = "Engine power must be at least 1")
    private Integer enginePower;

    @NotNull(message = "Engine displacement is required")
    @Min(value = 1, message = "Engine displacement must be at least 1")
    private Integer engineDisplacement;

    @NotNull(message = "Number of doors is required")
    @Min(value = 2, message = "Number of doors must be at least 2")
    @Max(value = 5, message = "Number of doors cannot exceed 5")
    private Integer numberOfDoors;

    @NotNull(message = "Number of seats is required")
    @Min(value = 1, message = "Number of seats must be at least 1")
    @Max(value = 9, message = "Number of seats cannot exceed 9")
    private Integer numberOfSeats;

    @NotNull(message = "Number of previous owners is required")
    @Min(value = 0, message = "Number of previous owners cannot be negative")
    private Integer numberOfPreviousOwners;

    @NotNull(message = "Registration date is required")
    private LocalDate firstRegistrationDate;

    @NotNull(message = "Last service date is required")
    private LocalDate lastServiceDate;

    @NotNull(message = "Next inspection date is required")
    private LocalDate nextInspectionDate;

    @Enumerated(EnumType.STRING)
    @NotNull(message = "Vehicle condition is required")
    private VehicleCondition condition;

    @Enumerated(EnumType.STRING)
    @NotNull(message = "Vehicle status is required")
    private VehicleStatus status;

    @ElementCollection
    @CollectionTable(name = "vehicle_images", joinColumns = @JoinColumn(name = "vehicle_id"))
    @Column(name = "image_url")
    private List<String> imageUrls;

    @ElementCollection
    @CollectionTable(name = "vehicle_features", joinColumns = @JoinColumn(name = "vehicle_id"))
    @Column(name = "feature")
    private List<String> features;

    @NotBlank(message = "Description is required")
    @Size(max = 2000, message = "Description must not exceed 2000 characters")
    private String description;

    @NotNull(message = "Location is required")
    @Embedded
    private Location location;

    @NotNull(message = "Dealer ID is required")
    private Long dealerId;

    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
        updatedAt = LocalDateTime.now();
    }

    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDateTime.now();
    }

    public enum FuelType {
        GASOLINE, DIESEL, ELECTRIC, HYBRID, LPG, CNG
    }

    public enum TransmissionType {
        MANUAL, AUTOMATIC, SEMI_AUTOMATIC, CVT
    }

    public enum BodyType {
        SEDAN, HATCHBACK, SUV, COUPE, CONVERTIBLE, WAGON, PICKUP, VAN, MINIVAN
    }

    public enum VehicleCondition {
        NEW, USED, DAMAGED, FOR_PARTS
    }

    public enum VehicleStatus {
        ACTIVE, SOLD, RESERVED, INACTIVE, PENDING_APPROVAL
    }

    @Embeddable
    public static class Location {
        @NotBlank(message = "City is required")
        private String city;

        @NotBlank(message = "State is required")
        private String state;

        @NotBlank(message = "Country is required")
        private String country;

        @NotBlank(message = "Postal code is required")
        private String postalCode;

        private String address;

        public Location() {}

        public Location(String city, String state, String country, String postalCode, String address) {
            this.city = city;
            this.state = state;
            this.country = country;
            this.postalCode = postalCode;
            this.address = address;
        }

        public String getCity() { return city; }
        public void setCity(String city) { this.city = city; }

        public String getState() { return state; }
        public void setState(String state) { this.state = state; }

        public String getCountry() { return country; }
        public void setCountry(String country) { this.country = country; }

        public String getPostalCode() { return postalCode; }
        public void setPostalCode(String postalCode) { this.postalCode = postalCode; }

        public String getAddress() { return address; }
        public void setAddress(String address) { this.address = address; }
    }

    public VehicleEntity() {}

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getBrand() { return brand; }
    public void setBrand(String brand) { this.brand = brand; }

    public String getModel() { return model; }
    public void setModel(String model) { this.model = model; }

    public Integer getYear() { return year; }
    public void setYear(Integer year) { this.year = year; }

    public BigDecimal getPrice() { return price; }
    public void setPrice(BigDecimal price) { this.price = price; }

    public Long getMileage() { return mileage; }
    public void setMileage(Long mileage) { this.mileage = mileage; }

    public FuelType getFuelType() { return fuelType; }
    public void setFuelType(FuelType fuelType) { this.fuelType = fuelType; }

    public TransmissionType getTransmission() { return transmission; }
    public void setTransmission(TransmissionType transmission) { this.transmission = transmission; }

    public BodyType getBodyType() { return bodyType; }
    public void setBodyType(BodyType bodyType) { this.bodyType = bodyType; }

    public Integer getEnginePower() { return enginePower; }
    public void setEnginePower(Integer enginePower) { this.enginePower = enginePower; }

    public Integer getEngineDisplacement() { return engineDisplacement; }
    public void setEngineDisplacement(Integer engineDisplacement) { this.engineDisplacement = engineDisplacement; }

    public Integer getNumberOfDoors() { return numberOfDoors; }
    public void setNumberOfDoors(Integer numberOfDoors) { this.numberOfDoors = numberOfDoors; }

    public Integer getNumberOfSeats() { return numberOfSeats; }
    public void setNumberOfSeats(Integer numberOfSeats) { this.numberOfSeats = numberOfSeats; }

    public Integer getNumberOfPreviousOwners() { return numberOfPreviousOwners; }
    public void setNumberOfPreviousOwners(Integer numberOfPreviousOwners) { this.numberOfPreviousOwners = numberOfPreviousOwners; }

    public LocalDate getFirstRegistrationDate() { return firstRegistrationDate; }
    public void setFirstRegistrationDate(LocalDate firstRegistrationDate) { this.firstRegistrationDate = firstRegistrationDate; }

    public LocalDate getLastServiceDate() { return lastServiceDate; }
    public void setLastServiceDate(LocalDate lastServiceDate) { this.lastServiceDate = lastServiceDate; }

    public LocalDate getNextInspectionDate() { return nextInspectionDate; }
    public void setNextInspectionDate(LocalDate nextInspectionDate) { this.nextInspectionDate = nextInspectionDate; }

    public VehicleCondition getCondition() { return condition; }
    public void setCondition(VehicleCondition condition) { this.condition = condition; }

    public VehicleStatus getStatus() { return status; }
    public void setStatus(VehicleStatus status) { this.status = status; }

    public List<String> getImageUrls() { return imageUrls; }
    public void setImageUrls(List<String> imageUrls) { this.imageUrls = imageUrls; }

    public List<String> getFeatures() { return features; }
    public void setFeatures(List<String> features) { this.features = features; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public Location getLocation() { return location; }
    public void setLocation(Location location) { this.location = location; }

    public Long getDealerId() { return dealerId; }
    public void setDealerId(Long dealerId) { this.dealerId = dealerId; }

    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }

    public LocalDateTime getUpdatedAt() { return updatedAt; }
    public void setUpdatedAt(LocalDateTime updatedAt) { this.updatedAt = updatedAt; }
}
