package org.transcom.services.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.transcom.dto.TruckDtoRequest;
import org.transcom.entities.Truck;
import org.transcom.entities.enums.TruckStatus;
import org.transcom.exceptions.TruckNotFoundException;
import org.transcom.exceptions.enums.ErrorMessages;
import org.transcom.mappers.TruckMapper;
import org.transcom.repositories.TruckRepository;
import org.transcom.repositories.TruckTypeRepository;
import org.transcom.services.TruckService;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class TruckServiceImpl implements TruckService {

    private final TruckRepository truckRepository;
    private final TruckTypeRepository truckTypeRepository;
    private final TruckMapper truckMapper;

    @Override
    public Truck createTruck(TruckDtoRequest truckDtoRequest) {
        Truck newTruck = truckMapper.truckWithTruckTypeEntity(truckDtoRequest, truckTypeRepository);
        return truckRepository.save(newTruck);
    }

    @Override
    public List<Truck> findAllTrucks() {
        return truckRepository.findAll();
    }

    @Override
    public Truck findTruckById(UUID id) {
        return truckRepository.findById(id).orElseThrow(() ->
                new TruckNotFoundException(ErrorMessages.TRUCK_NOT_FOUND.getMessage()));
    }

    @Override
    public Truck updateTruck(UUID id, TruckDtoRequest truckDtoRequest) {
        Truck truckToUpdate = findTruckById(id);
        Truck updateTruck = truckMapper.partialUpdate(truckDtoRequest, truckToUpdate);
        return truckRepository.save(updateTruck);
    }

    @Override
    public boolean deleteTruck(UUID id) {
        Truck truck = truckRepository.findById(id).orElse(null);
        if (truck != null) {
            truck.setTruckStatus(TruckStatus.DELETED);
            truckRepository.save(truck);
            return true;
        }
        return false;
    }
}
