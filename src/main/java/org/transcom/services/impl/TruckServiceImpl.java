package org.transcom.services.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.transcom.dto.TruckDtoRequest;
import org.transcom.dto.TruckDtoResponse;
import org.transcom.entities.Truck;
import org.transcom.entities.enums.TruckStatus;
import org.transcom.exceptions.TruckNotFoundException;
import org.transcom.exceptions.enums.ErrorMessages;
import org.transcom.mappers.TruckMapper;
import org.transcom.repositories.TruckRepository;
import org.transcom.repositories.TruckTypeRepository;
import org.transcom.repositories.UserRepository;
import org.transcom.services.TruckService;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class TruckServiceImpl implements TruckService {

    private final TruckRepository truckRepository;
    private final TruckTypeRepository truckTypeRepository;
    private final TruckMapper truckMapper;
    private final UserRepository userRepository;

    @Override
    public TruckDtoResponse createTruck(TruckDtoRequest truckDtoRequest) {
        Truck newTruck = truckMapper.truckWithTruckTypeAndUser(truckDtoRequest, truckTypeRepository, userRepository);
        Truck savedTruck = truckRepository.save(newTruck);
        return truckMapper.toTruckDtoResponse(savedTruck);
    }

    @Override
    public List<TruckDtoResponse> findAllTrucks() {
        List<Truck> trucks = truckRepository.findAll();
        return trucks.stream()
                .map(truckMapper::toTruckDtoResponse)
                .toList();
    }

    @Override
    public List<TruckDtoResponse> findTrucksByTruckTypeShortName(String truckTypeShortName) {
        List<Truck> trucks = truckRepository.findByTruckTypeShortName(truckTypeShortName);
        return trucks.stream()
                .map(truckMapper::toTruckDtoResponse)
                .toList();
    }

    @Override
    public TruckDtoResponse findTruckById(UUID id) {
        Truck truckById = truckRepository.findById(id).orElseThrow(() ->
                new TruckNotFoundException(ErrorMessages.TRUCK_NOT_FOUND.getMessage()));
        return truckMapper.toTruckDtoResponse(truckById);
    }

    @Override
    public TruckDtoResponse updateTruck(UUID id, TruckDtoRequest truckDtoRequest) {
        Truck truckToUpdate = truckRepository.findById(id).orElseThrow(() ->
                new TruckNotFoundException(ErrorMessages.TRUCK_NOT_FOUND.getMessage()));
        truckMapper.updateTruckFromDtoRequest(truckDtoRequest, truckToUpdate, truckTypeRepository, userRepository);
        Truck updatedTruck = truckRepository.save(truckToUpdate);
        return truckMapper.toTruckDtoResponse(updatedTruck);
    }

    @Override
    public boolean deleteTruck(UUID id) {
        Truck truckById = truckRepository.findById(id).orElseThrow(() ->
                new TruckNotFoundException(ErrorMessages.TRUCK_NOT_FOUND.getMessage()));
        if (truckById != null) {
            truckById.setTruckStatus(TruckStatus.DELETED);
            truckRepository.save(truckById);
            return true;
        }
        return false;
    }
}
