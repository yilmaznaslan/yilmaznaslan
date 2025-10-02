package com.yilmaznaslan.springdemo.vehicle;

public record Coupe( String type) implements VehicleSealed {
    @Override
    public String getType() {
        return type;
    }
}