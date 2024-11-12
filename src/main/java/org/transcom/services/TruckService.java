package org.transcom.services;

import org.transcom.dto.TruckDto;

import java.util.List;
import java.util.UUID;

public interface TruckService {
    TruckDto createTruck(TruckDto truckDto);

    TruckDto findTruckById(UUID id);

    List<TruckDto> findAllTrucks();

    TruckDto updateTruck(UUID id, TruckDto truckDto);

    void deleteTruck(UUID id);
}
