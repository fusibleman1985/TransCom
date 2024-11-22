package org.transcom.services;

import org.transcom.dto.TruckDtoRequest;
import org.transcom.dto.TruckDtoResponse;

import java.util.List;
import java.util.UUID;

public interface TruckService {

    TruckDtoResponse createTruck(TruckDtoRequest truckDtoRequest);

    TruckDtoResponse findTruckById(UUID id);

    List<TruckDtoResponse> findAllTrucks();

    List<TruckDtoResponse> findTrucksByTruckTypeShortName(String truckTypeShortName);

    TruckDtoResponse updateTruck(UUID id, TruckDtoRequest truckDtoRequest);

    boolean deleteTruck(UUID id);

}
