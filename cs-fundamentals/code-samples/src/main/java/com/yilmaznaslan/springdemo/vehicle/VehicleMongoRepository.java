package com.yilmaznaslan.springdemo.vehicle;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

public interface VehicleMongoRepository extends MongoRepository<VehicleDocument, String> {

    @Query("{'brand': ?0}")
    List<VehicleDocument> findByBrand(String brand);

    @Query("{'brand': ?0, 'model': ?1}")
    List<VehicleDocument> findByBrandAndModel(String brand, String model);

    @Query("{'price': {$gte: ?0, $lte: ?1}}")
    Page<VehicleDocument> findByPriceBetween(BigDecimal minPrice, BigDecimal maxPrice, Pageable pageable);

    @Query("{'year': {$gte: ?0, $lte: ?1}}")
    Page<VehicleDocument> findByYearBetween(Integer minYear, Integer maxYear, Pageable pageable);

    @Query("{'mileage': {$gte: ?0, $lte: ?1}}")
    Page<VehicleDocument> findByMileageBetween(Long minMileage, Long maxMileage, Pageable pageable);

    @Query("{'fuelType': {$in: ?0}}")
    Page<VehicleDocument> findByFuelTypeIn(List<VehicleEntity.FuelType> fuelTypes, Pageable pageable);

    @Query("{'transmission': {$in: ?0}}")
    Page<VehicleDocument> findByTransmissionIn(List<VehicleEntity.TransmissionType> transmissions, Pageable pageable);

    @Query("{'bodyType': {$in: ?0}}")
    Page<VehicleDocument> findByBodyTypeIn(List<VehicleEntity.BodyType> bodyTypes, Pageable pageable);

    @Query("{'condition': {$in: ?0}}")
    Page<VehicleDocument> findByConditionIn(List<VehicleEntity.VehicleCondition> conditions, Pageable pageable);

    @Query("{'status': {$in: ?0}}")
    Page<VehicleDocument> findByStatusIn(List<VehicleEntity.VehicleStatus> statuses, Pageable pageable);

    @Query("{'location.city': ?0}")
    Page<VehicleDocument> findByLocationCity(String city, Pageable pageable);

    @Query("{'location.state': ?0}")
    Page<VehicleDocument> findByLocationState(String state, Pageable pageable);

    @Query("{'location.country': ?0}")
    Page<VehicleDocument> findByLocationCountry(String country, Pageable pageable);

    @Query("{'dealerId': ?0}")
    Page<VehicleDocument> findByDealerId(Long dealerId, Pageable pageable);

    @Query("{'$text': {'$search': ?0}}")
    Page<VehicleDocument> findByTextSearch(String searchText, Pageable pageable);

    @Query("{'$and': [{'brand': {$regex: ?0, $options: 'i'}}, {'model': {$regex: ?1, $options: 'i'}}]}")
    Page<VehicleDocument> findByBrandAndModelContainingIgnoreCase(String brand, String model, Pageable pageable);

    @Query("{'$and': [{'price': {$gte: ?0, $lte: ?1}}, {'year': {$gte: ?2, $lte: ?3}}, {'mileage': {$gte: ?4, $lte: ?5}}]}")
    Page<VehicleDocument> findByPriceAndYearAndMileageBetween(BigDecimal minPrice, BigDecimal maxPrice, 
                                                             Integer minYear, Integer maxYear, 
                                                             Long minMileage, Long maxMileage, 
                                                             Pageable pageable);

    @Query("{'$and': [{'fuelType': {$in: ?0}}, {'transmission': {$in: ?1}}, {'bodyType': {$in: ?2}}]}")
    Page<VehicleDocument> findByFuelTypeAndTransmissionAndBodyTypeIn(List<VehicleEntity.FuelType> fuelTypes,
                                                                     List<VehicleEntity.TransmissionType> transmissions,
                                                                     List<VehicleEntity.BodyType> bodyTypes,
                                                                     Pageable pageable);

    @Query("{'$and': [{'condition': {$in: ?0}}, {'status': {$in: ?1}}]}")
    Page<VehicleDocument> findByConditionAndStatusIn(List<VehicleEntity.VehicleCondition> conditions,
                                                     List<VehicleEntity.VehicleStatus> statuses,
                                                     Pageable pageable);

    @Query("{'$and': [{'location.city': ?0}, {'location.state': ?1}, {'location.country': ?2}]}")
    Page<VehicleDocument> findByLocation(String city, String state, String country, Pageable pageable);

    @Query(value = "{'$text': {'$search': ?0}}", sort = "{'score': {'$meta': 'textScore'}}")
    Page<VehicleDocument> findByTextSearchWithScore(String searchText, Pageable pageable);

    @Query("{'features': {$in: ?0}}")
    Page<VehicleDocument> findByFeaturesIn(List<String> features, Pageable pageable);

    @Query("{'enginePower': {$gte: ?0, $lte: ?1}}")
    Page<VehicleDocument> findByEnginePowerBetween(Integer minEnginePower, Integer maxEnginePower, Pageable pageable);

    @Query("{'engineDisplacement': {$gte: ?0, $lte: ?1}}")
    Page<VehicleDocument> findByEngineDisplacementBetween(Integer minEngineDisplacement, Integer maxEngineDisplacement, Pageable pageable);

    @Query("{'numberOfDoors': {$gte: ?0, $lte: ?1}}")
    Page<VehicleDocument> findByNumberOfDoorsBetween(Integer minDoors, Integer maxDoors, Pageable pageable);

    @Query("{'numberOfSeats': {$gte: ?0, $lte: ?1}}")
    Page<VehicleDocument> findByNumberOfSeatsBetween(Integer minSeats, Integer maxSeats, Pageable pageable);

    @Query("{'numberOfPreviousOwners': {$gte: ?0, $lte: ?1}}")
    Page<VehicleDocument> findByNumberOfPreviousOwnersBetween(Integer minPreviousOwners, Integer maxPreviousOwners, Pageable pageable);
}
