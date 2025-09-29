package com.yilmaznaslan.springdemo.vehicle;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.TextIndexed;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "vehicles")
public class VehicleDocument {

    @Id
    private String id;

    @TextIndexed
    private String brand;

    @TextIndexed
    private String model;

    private Integer year;
    private BigDecimal price;
    private Long mileage;
    private VehicleEntity.FuelType fuelType;
    private VehicleEntity.TransmissionType transmission;
    private VehicleEntity.BodyType bodyType;
    private Integer enginePower;
    private Integer engineDisplacement;
    private Integer numberOfDoors;
    private Integer numberOfSeats;
    private Integer numberOfPreviousOwners;
    private LocalDate firstRegistrationDate;
    private LocalDate lastServiceDate;
    private LocalDate nextInspectionDate;
    private VehicleEntity.VehicleCondition condition;
    private VehicleEntity.VehicleStatus status;
    private List<String> imageUrls;
    private List<String> features;

    @TextIndexed
    private String description;

    private Location location;
    private Long dealerId;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public static class Location {
        private String city;
        private String state;
        private String country;
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

    public VehicleDocument() {}

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

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

    public VehicleEntity.FuelType getFuelType() { return fuelType; }
    public void setFuelType(VehicleEntity.FuelType fuelType) { this.fuelType = fuelType; }

    public VehicleEntity.TransmissionType getTransmission() { return transmission; }
    public void setTransmission(VehicleEntity.TransmissionType transmission) { this.transmission = transmission; }

    public VehicleEntity.BodyType getBodyType() { return bodyType; }
    public void setBodyType(VehicleEntity.BodyType bodyType) { this.bodyType = bodyType; }

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

    public VehicleEntity.VehicleCondition getCondition() { return condition; }
    public void setCondition(VehicleEntity.VehicleCondition condition) { this.condition = condition; }

    public VehicleEntity.VehicleStatus getStatus() { return status; }
    public void setStatus(VehicleEntity.VehicleStatus status) { this.status = status; }

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

