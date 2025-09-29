package com.yilmaznaslan.springdemo.vehicle;

public sealed interface VehicleSealed permits Coupe, Van{

    String getType();
}
