package com.yilmaznaslan.springdemo.errorhandling.exceptions;

public class VehicleNotFoundException extends RuntimeException{

    private final long vehicleId;

    public VehicleNotFoundException(long vehicleId) {
        super();
        this.vehicleId = vehicleId;
    }

    public long getVehicleId() {
        return vehicleId;
    }
}
