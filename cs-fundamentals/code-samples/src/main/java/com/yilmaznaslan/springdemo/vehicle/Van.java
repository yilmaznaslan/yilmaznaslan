package com.yilmaznaslan.springdemo.vehicle;

public record Van(String type) implements VehicleSealed {
    @Override
    public String getType() {
        return type;
    }
}