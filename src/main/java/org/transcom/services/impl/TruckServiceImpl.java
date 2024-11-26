package org.transcom.services.impl;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.transcom.dto.TruckDtoRequest;
import org.transcom.dto.TruckDtoResponse;
import org.transcom.entities.Truck;
import org.transcom.entities.enums.TruckStatus;
import org.transcom.exceptions.TruckNotFoundException;
import org.transcom.mappers.TruckMapper;
import org.transcom.repositories.FavoriteRepository;
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
    private final FavoriteRepository favoriteRepository;

    @Override
    public TruckDtoResponse createTruck(TruckDtoRequest truckDtoRequest) {
        Truck newTruck = truckMapper.toCreatedTruck(truckDtoRequest, truckTypeRepository, userRepository);
        return truckMapper.toTruckDtoResponse(truckRepository.save(newTruck));
    }

    @Override
    public List<TruckDtoResponse> findAllTrucks() {
        return truckRepository.findAll().stream()
                .map(truck -> truckMapper.toTruckDtoResponse(truck, favoriteRepository))
                .toList();
    }

    @Override
    public List<TruckDtoResponse> findTrucksByTruckTypeShortName(String truckTypeShortName) {
        return truckRepository.findByTruckTypeShortName(truckTypeShortName).stream()
                .map(truck -> truckMapper.toTruckDtoResponse(truck, favoriteRepository))
                .toList();
    }

    @Override
    public TruckDtoResponse findTruckById(UUID id) {
        Truck truckById = truckRepository.findById(id)
                .orElseThrow(() -> new TruckNotFoundException("{error.truck_not_found}"));
        return truckMapper.toTruckDtoResponse(truckById, favoriteRepository);
    }

    @Override
    @Transactional
    public TruckDtoResponse updateTruck(UUID id, TruckDtoRequest truckDtoRequest) {
        Truck truckToUpdate = truckRepository.findById(id).orElseThrow(() ->
                new TruckNotFoundException("{error.truck_not_found}"));
        truckMapper.updateTruckFromDtoRequest(truckDtoRequest, truckToUpdate, truckTypeRepository, userRepository);
        Truck updatedTruck = truckRepository.save(truckToUpdate);
        return truckMapper.toTruckDtoResponse(updatedTruck, favoriteRepository);
    }

    @Override
    @Transactional
    public boolean deleteTruck(UUID id) {
        Truck truckById = truckRepository.findById(id).orElseThrow(() ->
                new TruckNotFoundException("{error.truck_not_found}"));
        if (truckById.getTruckStatus() != TruckStatus.DELETED) {
            truckById.setTruckStatus(TruckStatus.DELETED);
            truckRepository.save(truckById);
            return true;
        }
        return false;
    }
}
