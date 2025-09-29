package com.yilmaznaslan.springdemo.vehicle;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

@Service
public class VehicleMongoService {

    private final VehicleMongoRepository repository;
    private final VehicleMapper vehicleMapper;

    public VehicleMongoService(VehicleMongoRepository repository, VehicleMapper vehicleMapper) {
        this.repository = repository;
        this.vehicleMapper = vehicleMapper;
    }

    public VehicleSearchResponse searchVehicles(VehicleSearchRequest searchRequest) {
        final Pageable pageable = createPageable(searchRequest);
        Page<VehicleDocument> vehiclePage = performSearch(searchRequest, pageable);
        
        return VehicleSearchResponse.builder()
                .vehicles(vehiclePage.getContent().stream()
                        .map(vehicleMapper::toDto)
                        .toList())
                .totalElements(vehiclePage.getTotalElements())
                .totalPages(vehiclePage.getTotalPages())
                .currentPage(vehiclePage.getNumber())
                .size(vehiclePage.getSize())
                .first(vehiclePage.isFirst())
                .last(vehiclePage.isLast())
                .numberOfElements(vehiclePage.getNumberOfElements())
                .build();
    }

    public Optional<VehicleDto> findById(String id) {
        return repository.findById(id)
                .map(vehicleMapper::toDto);
    }

    public VehicleDto createVehicle(CreateVehicleDto createVehicleDto) {
        final VehicleDocument vehicleDocument = vehicleMapper.toDocument(createVehicleDto);
        vehicleDocument.setStatus(VehicleEntity.VehicleStatus.PENDING_APPROVAL);
        final VehicleDocument savedVehicle = repository.save(vehicleDocument);
        return vehicleMapper.toDto(savedVehicle);
    }

    public Optional<VehicleDto> updateVehicle(String id, CreateVehicleDto updateVehicleDto) {
        return repository.findById(id)
                .map(existingVehicle -> {
                    vehicleMapper.updateDocument(updateVehicleDto, existingVehicle);
                    return repository.save(existingVehicle);
                })
                .map(vehicleMapper::toDto);
    }

    public boolean deleteVehicle(String id) {
        if (repository.existsById(id)) {
            repository.deleteById(id);
            return true;
        }
        return false;
    }

    public Page<VehicleDto> findByDealerId(Long dealerId, Pageable pageable) {
        return repository.findByDealerId(dealerId, pageable)
                .map(vehicleMapper::toDto);
    }

    public Page<VehicleDto> findByBrand(String brand, Pageable pageable) {
        final List<VehicleDto> vehicles = repository.findByBrand(brand)
                .stream()
                .map(vehicleMapper::toDto)
                .toList();
        return new org.springframework.data.domain.PageImpl<>(vehicles, pageable, vehicles.size());
    }

    public Page<VehicleDto> findByPriceRange(BigDecimal minPrice, BigDecimal maxPrice, Pageable pageable) {
        return repository.findByPriceBetween(minPrice, maxPrice, pageable)
                .map(vehicleMapper::toDto);
    }

    public Page<VehicleDto> findByYearRange(Integer minYear, Integer maxYear, Pageable pageable) {
        return repository.findByYearBetween(minYear, maxYear, pageable)
                .map(vehicleMapper::toDto);
    }

    public Page<VehicleDto> findByMileageRange(Long minMileage, Long maxMileage, Pageable pageable) {
        return repository.findByMileageBetween(minMileage, maxMileage, pageable)
                .map(vehicleMapper::toDto);
    }

    public Page<VehicleDto> findByLocation(String city, String state, String country, Pageable pageable) {
        return repository.findByLocation(city, state, country, pageable)
                .map(vehicleMapper::toDto);
    }

    public Page<VehicleDto> findByTextSearch(String searchText, Pageable pageable) {
        return repository.findByTextSearchWithScore(searchText, pageable)
                .map(vehicleMapper::toDto);
    }

    public Page<VehicleDto> findByAdvancedFilters(VehicleSearchRequest searchRequest, Pageable pageable) {
        return repository.findByPriceAndYearAndMileageBetween(
                searchRequest.getMinPrice(), searchRequest.getMaxPrice(),
                searchRequest.getMinYear(), searchRequest.getMaxYear(),
                searchRequest.getMinMileage(), searchRequest.getMaxMileage(),
                pageable)
                .map(vehicleMapper::toDto);
    }

    private Pageable createPageable(VehicleSearchRequest searchRequest) {
        final Sort sort = Sort.by(
                searchRequest.getSortDirection() == VehicleSearchRequest.SortDirection.ASC 
                        ? Sort.Direction.ASC 
                        : Sort.Direction.DESC,
                mapSortField(searchRequest.getSortField())
        );
        return PageRequest.of(searchRequest.getPage(), searchRequest.getSize(), sort);
    }

    private String mapSortField(VehicleSearchRequest.SortField sortField) {
        return switch (sortField) {
            case PRICE -> "price";
            case MILEAGE -> "mileage";
            case YEAR -> "year";
            case CREATED_AT -> "createdAt";
            case UPDATED_AT -> "updatedAt";
            case BRAND -> "brand";
            case MODEL -> "model";
        };
    }

    private Page<VehicleDocument> performSearch(VehicleSearchRequest searchRequest, Pageable pageable) {
        if (StringUtils.hasText(searchRequest.getQuery())) {
            return repository.findByTextSearchWithScore(searchRequest.getQuery(), pageable);
        }

        if (hasBasicFilters(searchRequest)) {
            return repository.findByPriceAndYearAndMileageBetween(
                    searchRequest.getMinPrice() != null ? searchRequest.getMinPrice() : BigDecimal.ZERO,
                    searchRequest.getMaxPrice() != null ? searchRequest.getMaxPrice() : new BigDecimal("999999999"),
                    searchRequest.getMinYear() != null ? searchRequest.getMinYear() : 1900,
                    searchRequest.getMaxYear() != null ? searchRequest.getMaxYear() : 2025,
                    searchRequest.getMinMileage() != null ? searchRequest.getMinMileage() : 0L,
                    searchRequest.getMaxMileage() != null ? searchRequest.getMaxMileage() : Long.MAX_VALUE,
                    pageable);
        }

        return repository.findAll(pageable);
    }

    private boolean hasBasicFilters(VehicleSearchRequest searchRequest) {
        return searchRequest.getMinPrice() != null || searchRequest.getMaxPrice() != null ||
               searchRequest.getMinYear() != null || searchRequest.getMaxYear() != null ||
               searchRequest.getMinMileage() != null || searchRequest.getMaxMileage() != null;
    }
}