package org.transcom.services;

import org.transcom.dto.TruckDtoRequest;
import org.transcom.entities.Truck;

import java.util.List;
import java.util.UUID;

public interface TruckService {

    Truck createTruck(TruckDtoRequest truckDtoRequest);

    Truck findTruckById(UUID id);

    List<Truck> findAllTrucks();

    Truck updateTruck(UUID id, TruckDtoRequest truckDtoRequest);

    boolean deleteTruck(UUID id);
}
