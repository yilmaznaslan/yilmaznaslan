package com.yilmaznaslan.springdemo.vehicle;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "spring", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface VehicleMapper {

    @Mapping(source = "id", target = "id")
    VehicleDto toDto(VehicleDocument vehicleDocument);

    @Mapping(source = "id", target = "id")
    VehicleDto mapToDto(VehicleDocument vehicleDocument);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "status", ignore = true)
    VehicleDocument toDocument(CreateVehicleDto createVehicleDto);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "status", ignore = true)
    VehicleDocument mapToDocument(CreateVehicleDto createVehicleDto);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    void updateDocument(CreateVehicleDto updateDto, @MappingTarget VehicleDocument existingDocument);

    VehicleDto.LocationDto toLocationDto(VehicleEntity.Location location);

    VehicleEntity.Location toLocationEntity(VehicleDto.LocationDto locationDto);

    VehicleEntity.Location toLocationEntity(CreateVehicleDto.LocationDto locationDto);
}
